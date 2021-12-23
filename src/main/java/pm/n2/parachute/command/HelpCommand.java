package pm.n2.parachute.command;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import pm.n2.parachute.ParachuteCommands;
import pm.n2.parachute.util.FakeCommandSource;

import java.util.Map;

public class HelpCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.help.failed"));

    // Copied from vanilla
    public static void register(CommandDispatcher<FakeCommandSource> dispatcher) {
        dispatcher.register(ParachuteCommands
                .literal("help")
                .executes(ctx -> {
                    Map<CommandNode<FakeCommandSource>, String> map = dispatcher.getSmartUsage(dispatcher.getRoot(), ctx.getSource());

                    for (String string : map.values()) {
                        ctx.getSource().sendFeedback(new LiteralText(ParachuteCommands.COMMAND_PREFIX + string));
                    }

                    return map.size();
                }).then(ParachuteCommands.argument("command", StringArgumentType.greedyString()).executes((ctx) -> {
                    ParseResults<FakeCommandSource> parseResults = dispatcher.parse(StringArgumentType.getString(ctx, "command"), ctx.getSource());
                    if (parseResults.getContext().getNodes().isEmpty()) {
                        throw FAILED_EXCEPTION.create();
                    } else {
                        Map<CommandNode<FakeCommandSource>, String> map = dispatcher.getSmartUsage((Iterables.getLast(parseResults.getContext().getNodes())).getNode(), ctx.getSource());

                        for (String string : map.values()) {
                            FakeCommandSource source = ctx.getSource();
                            source.sendFeedback(new LiteralText(ParachuteCommands.COMMAND_PREFIX + parseResults.getReader().getString() + " " + string));
                        }

                        return map.size();
                    }
                }))
        );
    }
}