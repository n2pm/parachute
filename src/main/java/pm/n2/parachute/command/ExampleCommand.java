package pm.n2.parachute.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.text.LiteralText;
import pm.n2.parachute.ParachuteCommands;

public class ExampleCommand {
    public static void register(CommandDispatcher<ParachuteCommands.FakeCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder) ParachuteCommands.literal("example")).executes(context -> execute((ParachuteCommands.FakeCommandSource) context.getSource())));
    }

    private static int execute(ParachuteCommands.FakeCommandSource source) {
        source.sendFeedback(new LiteralText("Hello from parachute! <3"));
        return 1;
    }
}