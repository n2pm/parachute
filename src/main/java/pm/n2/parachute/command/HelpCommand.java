package pm.n2.parachute.command;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import pm.n2.parachute.ParachuteCommands;

import java.util.Map;

public class HelpCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.help.failed"));

    // Copied vanilla code
    public static void register(CommandDispatcher<ParachuteCommands.FakeCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder) ParachuteCommands.literal("help").executes((context) -> {
            Map<CommandNode<ParachuteCommands.FakeCommandSource>, String> map = dispatcher.getSmartUsage(dispatcher.getRoot(), (ParachuteCommands.FakeCommandSource) context.getSource());

            for (String string : map.values()) {
                ((ParachuteCommands.FakeCommandSource) context.getSource()).sendFeedback(new LiteralText(ParachuteCommands.COMMAND_PREFIX + string));
            }

            return map.size();
        })).then(CommandManager.argument("command", StringArgumentType.greedyString()).executes((context) -> {
            ParseResults<ParachuteCommands.FakeCommandSource> parseResults = dispatcher.parse((String) StringArgumentType.getString(context, "command"), (ParachuteCommands.FakeCommandSource) context.getSource());
            if (parseResults.getContext().getNodes().isEmpty()) {
                throw FAILED_EXCEPTION.create();
            } else {
                Map<CommandNode<ParachuteCommands.FakeCommandSource>, String> map = dispatcher.getSmartUsage(((ParsedCommandNode) Iterables.getLast(parseResults.getContext().getNodes())).getNode(), (ParachuteCommands.FakeCommandSource) context.getSource());

                for (String string : map.values()) {
                    ParachuteCommands.FakeCommandSource source = (ParachuteCommands.FakeCommandSource) context.getSource();
                    String command = parseResults.getReader().getString();
                    source.sendFeedback(new LiteralText(ParachuteCommands.COMMAND_PREFIX + command + " " + string));
                }

                return map.size();
            }
        })));
    }
}