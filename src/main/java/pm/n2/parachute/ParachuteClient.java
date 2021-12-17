package pm.n2.parachute;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.math.BlockPos;

public class ParachuteClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
        Parachute.LOGGER.info("Hello from parachute <3 ! Minecraft's block pos code is still {} blocks off.", BlockPos.ORIGIN.getSquaredDistance(BlockPos.ORIGIN));
    }
}