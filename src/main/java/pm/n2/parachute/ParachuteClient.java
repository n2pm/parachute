package pm.n2.parachute;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Formatting;

public class ParachuteClient implements ClientModInitializer {
    private static String MOD_VERSION = "0.0.0";

    public static String getFormattedModVersion() {
        String version = getModVersion();
        Formatting color;

        if (version.contains("+") && version.split("\\+")[1].contains(".")) {
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

    @Override
    public void onInitializeClient() {
        ModContainer mod = FabricLoader.getInstance()
                .getModContainer("parachute")
                .orElseThrow(NullPointerException::new);

        MOD_VERSION = mod.getMetadata()
                .getVersion()
                .getFriendlyString();

        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
        Parachute.LOGGER.info("Hello from parachute <3!");
    }
}