package pm.n2.parachute.util;

import net.minecraft.util.math.BlockPos;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.network.WorldEditCUINetworkHandler;

public class WorldDataStorage {
    private static final WorldDataStorage INSTANCE = new WorldDataStorage();

    public static WorldDataStorage getInstance()
    {
        return INSTANCE;
    }

    private final BlockPos[] worldEditPos = {null,null};
    private String worldEditSelectionMode;


    public void reset(boolean isLogout) {
        this.resetWorldEditPos();
    }

    public void registerWorldEditCUIChannel() {
        Parachute.LOGGER.info("WorldDataStorage#registerWorldeditCUIChannel()");
        WorldEditCUINetworkHandler.registerReceiver();
    }

    public void onWorldJoin() {
        this.registerWorldEditCUIChannel();
    }

    public void setWorldEditPos(int pos, BlockPos blockPos) {
        this.worldEditPos[pos] = blockPos;
    }

    public void resetWorldEditPos() {
        this.worldEditPos[0] = null;
        this.worldEditPos[1] = null;
    }

    public BlockPos[] getWorldEditPos() {
        return worldEditPos;
    }

    public String getWorldEditSelectionMode() {
        return this.worldEditSelectionMode;
    }

    public void setWorldEditSelectionMode(String mode) {
        this.worldEditSelectionMode = mode;
    }
}
