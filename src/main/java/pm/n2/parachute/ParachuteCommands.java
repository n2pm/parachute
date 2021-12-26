package pm.n2.parachute;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import pm.n2.parachute.command.ExampleCommand;
import pm.n2.parachute.command.HelpCommand;
import pm.n2.parachute.command.ModsCommand;
import pm.n2.parachute.util.ClientCommandSource;

import java.util.HashMap;
import java.util.Map;

// I wish fabric client commands api was like this, then it'd actually be useful
public class ParachuteCommands {
    private static final ParachuteCommands INSTANCE = new ParachuteCommands();
    public static final char COMMAND_PREFIX = '.';

    public static ParachuteCommands getInstance() {
        return INSTANCE;
    }

    private final CommandDispatcher<ClientCommandSource> dispatcher = new CommandDispatcher<>();
    private final MinecraftClient client;

    public ParachuteCommands() {
        this.client = MinecraftClient.getInstance();
        ExampleCommand.register(this.dispatcher);
        HelpCommand.register(this.dispatcher);
        ModsCommand.register(this.dispatcher);
        this.dispatcher.findAmbiguities((parent, child, sibling, inputs) -> Parachute.LOGGER.warn("Ambiguity between arguments {} and {} with inputs: {}", this.dispatcher.getPath(child), this.dispatcher.getPath(sibling), inputs));
    }

    public static LiteralArgumentBuilder<ClientCommandSource> literal(String literal) {
        return LiteralArgumentBuilder.literal(literal);
    }

    public static <T> RequiredArgumentBuilder<ClientCommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public void execute(String command, ClientCommandSource source) {
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

    // Used by MixinCommandSuggestor
    public static boolean isClientCommandPrefix(String message) {
        if (message.startsWith(Character.toString(COMMAND_PREFIX) + COMMAND_PREFIX)
                || message.startsWith(COMMAND_PREFIX + "/"))
            return false;
        return message.startsWith(Character.toString(COMMAND_PREFIX));
    }

    public boolean executeCommand(String message) {
        // allow people to send ./
        if (message.startsWith(Character.toString(COMMAND_PREFIX)) && !message.startsWith(COMMAND_PREFIX + "/")) {
            if (message.startsWith(COMMAND_PREFIX + Character.toString(COMMAND_PREFIX))) {
                // allow people to send "." in chat, sometimes used to check if someones cheating or smth
                this.client.getNetworkHandler().sendPacket(new ChatMessageC2SPacket(message.substring(1)));
                return false;
            }
            execute(message, new ClientCommandSource(this.client));
            return false;
        }
        return true;
    }

    // Copied from vanilla
    public RootCommandNode<CommandSource> getCommandTree() {
        HashMap<CommandNode<ClientCommandSource>, CommandNode<CommandSource>> map = Maps.newHashMap();
        RootCommandNode<CommandSource> rootCommandNode = new RootCommandNode<>();
        map.put(this.dispatcher.getRoot(), rootCommandNode);
        this.createCommandTree(this.dispatcher.getRoot(), rootCommandNode, new ClientCommandSource(this.client), map);
        return rootCommandNode;
    }

    // Copied from vanilla
    private void createCommandTree(CommandNode<ClientCommandSource> tree, CommandNode<CommandSource> result, ClientCommandSource source, Map<CommandNode<ClientCommandSource>, CommandNode<CommandSource>> resultNodes) {
        for (CommandNode<ClientCommandSource> commandNode : tree.getChildren()) {
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
}
