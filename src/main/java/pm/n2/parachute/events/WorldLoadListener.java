package pm.n2.parachute.events;

import pm.n2.parachute.util.WorldDataStorage;
import fi.dy.masa.malilib.interfaces.IWorldLoadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

public class WorldLoadListener implements IWorldLoadListener {
    @Override
    public void onWorldLoadPost(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc) {
        // Clear the cached data
        WorldDataStorage.getInstance().reset(worldAfter == null);

        // Logging in to a world or changing dimensions or respawning
        if (worldAfter != null) {
            WorldDataStorage.getInstance().onWorldJoin();
        }
    }

}
