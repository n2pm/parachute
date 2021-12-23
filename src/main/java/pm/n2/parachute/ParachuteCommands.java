package pm.n2.parachute;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import pm.n2.parachute.command.ExampleCommand;
import pm.n2.parachute.command.HelpCommand;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// I wish fabric client commands api was like this, then it'd actually be useful
public class ParachuteCommands {
    private static final ParachuteCommands INSTANCE = new ParachuteCommands();
    public static final char COMMAND_PREFIX = '.';

    public static ParachuteCommands getInstance() {
        return INSTANCE;
    }

    private final CommandDispatcher<FakeCommandSource> dispatcher = new CommandDispatcher<>();

    public ParachuteCommands() {
        ExampleCommand.register(this.dispatcher);
        HelpCommand.register(this.dispatcher);
        this.dispatcher.findAmbiguities((parent, child, sibling, inputs) -> Parachute.LOGGER.warn("Ambiguity between arguments {} and {} with inputs: {}", this.dispatcher.getPath(child), this.dispatcher.getPath(sibling), inputs));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> literal(String literal) {
        return LiteralArgumentBuilder.literal(literal);
    }

    public void execute(String command, FakeCommandSource source) {
        StringReader stringReader = new StringReader(command);
        if (stringReader.canRead() && stringReader.peek() == COMMAND_PREFIX) {
            stringReader.skip();
        }
        try {
            this.dispatcher.execute(stringReader, source);
        } catch (CommandException commandException) {
            source.sendError(commandException.getTextMessage());
        } catch (CommandSyntaxException commandException) {
            source.sendError(Texts.toText(commandException.getRawMessage()));
            if (commandException.getInput() != null && commandException.getCursor() >= 0) {
                int position = Math.min(commandException.getInput().length(), commandException.getCursor());
                MutableText mutableText = new LiteralText("").formatted(Formatting.GRAY).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)));
                if (position > 10) {
                    mutableText.append("...");
                }
                mutableText.append(commandException.getInput().substring(Math.max(0, position - 10), position));
                if (position < commandException.getInput().length()) {
                    MutableText text = new LiteralText(commandException.getInput().substring(position)).formatted(Formatting.RED, Formatting.UNDERLINE);
                    mutableText.append(text);
                }
                mutableText.append(new TranslatableText("command.context.here").formatted(Formatting.RED, Formatting.ITALIC));
                source.sendError(mutableText);
            }
        }
    }

    public boolean executeCommand(String message) {
        if (message.charAt(0) == COMMAND_PREFIX) {
            execute(message, new FakeCommandSource(MinecraftClient.getInstance()));
            return false;
        }
        return true;
    }

    public RootCommandNode<CommandSource> getCommandTree() {
        HashMap<CommandNode<FakeCommandSource>, CommandNode<CommandSource>> map = Maps.newHashMap();
        RootCommandNode<CommandSource> rootCommandNode = new RootCommandNode<>();
        map.put(this.dispatcher.getRoot(), rootCommandNode);
        this.createCommandTree(this.dispatcher.getRoot(), rootCommandNode, new FakeCommandSource(MinecraftClient.getInstance()), map);
        return rootCommandNode;
    }

    // Copied from vanilla
    private void createCommandTree(CommandNode<FakeCommandSource> tree, CommandNode<CommandSource> result, FakeCommandSource source, Map<CommandNode<FakeCommandSource>, CommandNode<CommandSource>> resultNodes) {
        for (CommandNode<FakeCommandSource> commandNode : tree.getChildren()) {
            if (commandNode.canUse(source)) {
                ArgumentBuilder<CommandSource, ?> argumentBuilder = (ArgumentBuilder) commandNode.createBuilder();
                argumentBuilder.executes((context) -> 0);
                if (argumentBuilder.getCommand() != null) {
                    argumentBuilder.executes((context) -> 0);
                }

                if (argumentBuilder instanceof RequiredArgumentBuilder) {
                    RequiredArgumentBuilder<CommandSource, ?> requiredArgumentBuilder = (RequiredArgumentBuilder) argumentBuilder;
                    if (requiredArgumentBuilder.getSuggestionsProvider() != null) {
                        requiredArgumentBuilder.suggests(SuggestionProviders.getLocalProvider(requiredArgumentBuilder.getSuggestionsProvider()));
                    }
                }

                if (argumentBuilder.getRedirect() != null) {
                    argumentBuilder.redirect(resultNodes.get(argumentBuilder.getRedirect()));
                }

                CommandNode<CommandSource> requiredArgumentBuilder = argumentBuilder.build();
                resultNodes.put(commandNode, requiredArgumentBuilder);
                result.addChild(requiredArgumentBuilder);
                if (!commandNode.getChildren().isEmpty()) {
                    this.createCommandTree(commandNode, requiredArgumentBuilder, source, resultNodes);
                }
            }
        }
    }

    public CommandDispatcher<FakeCommandSource> getDispatcher() {
        return this.dispatcher;
    }

    public static class FakeCommandSource extends ServerCommandSource {
        private final MinecraftClient client;
        private final ChatHud chatHud;

        public FakeCommandSource(MinecraftClient client) {
            super(client.player, client.player.getPos(), client.player.getRotationClient(), null, 0, client.player.getEntityName(), client.player.getName(), null, client.player);
            this.client = MinecraftClient.getInstance();
            this.chatHud = this.client.inGameHud.getChatHud();
        }

        public Collection<String> getPlayerNames() {
            return client.getNetworkHandler().getPlayerList().stream().map(e -> e.getProfile().getName()).collect(Collectors.toList());
        }

        public void sendFeedback(Text message) {
            this.chatHud.addMessage(message);
        }

        public void sendFeedback(Text message, Boolean broadcastToOps) {
            this.chatHud.addMessage(message);
        }

        public void sendError(Text message) {
            this.chatHud.addMessage(new LiteralText("").append(message).formatted(Formatting.RED));
        }
    }
}
