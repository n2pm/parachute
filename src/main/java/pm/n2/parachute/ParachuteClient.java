package pm.n2.parachute;

import com.adryd.cauldron.api.command.ClientCommandManager;
import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Formatting;
import pm.n2.parachute.command.ModsCommand;
import pm.n2.parachute.command.PanoramaCommand;

public class ParachuteClient implements ClientModInitializer {
    private static String MOD_VERSION = "0.0.0";

    public static String getFormattedModVersion() {
        String version = getModVersion();
        Formatting color;

        if (version.contains("+SNAPSHOT")) {
            // dev build
            color = Formatting.RED;
        } else if (version.contains("+")) {
            // ci build
            color = Formatting.LIGHT_PURPLE;
        } else {
            // release build
            color = Formatting.GREEN;
        }

        return color + version + Formatting.RESET;
    }

    public static String getModVersion() {
        return MOD_VERSION;
    }

    public void onInitializeClient() {
        MOD_VERSION = FabricLoader
                .getInstance()
                .getModContainer("parachute")
                .get()
                .getMetadata()
                .getVersion()
                .toString();

        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
        Parachute.LOGGER.info("Hello from parachute <3!");

        PanoramaCommand.register(ClientCommandManager.DISPATCHER);
        ModsCommand.register(ClientCommandManager.DISPATCHER);
    }
}