package pm.n2.parachute.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.text.LiteralText;
import pm.n2.parachute.ParachuteCommands;
import pm.n2.parachute.util.ClientCommandSource;

public class ExampleCommand {
    public static void register(CommandDispatcher<ClientCommandSource> dispatcher) {
        dispatcher.register(ParachuteCommands.literal("example").executes(context -> execute(context.getSource())));
    }

    private static int execute(ClientCommandSource source) {
        source.sendFeedback(new LiteralText("Hello from parachute! <3"));
        return 1;
    }
}