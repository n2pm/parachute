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

    private BlockPos[] worldEditPos = {null,null};


    public void reset(boolean isLogout) {
        this.resetWorldEditPos();
    }

    public void registerWorldEditCUIChannel() {
        Parachute.LOGGER.info("WorldDataStorage#registerWorldeditCUIChannel()");
        WorldEditCUINetworkHandler.registerReciever();
    }

    public void onWorldJoin() {
        this.registerWorldEditCUIChannel();
    }

    public void setWorldEditPos(int pos, BlockPos blockPos) {
        this.worldEditPos[pos] = blockPos;
    }

    public void resetWorldEditPos() {
        worldEditPos[0] = null;
        worldEditPos[1] = null;
    }

    public BlockPos[] getWorldEditPos() {
        return worldEditPos;
    }
}
