package pm.n2.parachute;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;

public class ParachuteClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
        Parachute.LOGGER.info("Hello from parachute <3!");
    }
}