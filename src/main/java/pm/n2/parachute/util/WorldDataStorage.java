package pm.n2.parachute.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.network.WorldeditCUINetworkHandler;

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

    public void registerWorldeditCUIChannel() {
        Parachute.LOGGER.info("WorldDataStorage#registerWorldeditCUIChannel()");
        WorldeditCUINetworkHandler.registerReciever();
    }

    public void onWorldJoin() {
        this.registerWorldeditCUIChannel();
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
