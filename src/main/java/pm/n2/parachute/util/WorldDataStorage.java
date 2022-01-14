package pm.n2.parachute.util;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import net.minecraft.util.math.BlockPos;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.network.WorldEditCUINetworkHandler;

public class WorldDataStorage {
    private static final WorldDataStorage INSTANCE = new WorldDataStorage();
    private final BlockPos[] worldEditPos = {null, null};
    private String worldEditSelectionMode;
    private boolean inGame;

    public static WorldDataStorage getInstance() {
        return INSTANCE;
    }

    public void reset(boolean isLogout) {
        this.resetWorldEditPos();
        this.inGame = !isLogout;
    }

    public void onWorldJoin() {
        this.inGame = true;
        if (Configs.FeatureConfigs.WORLDEDIT_CUI.getBooleanValue()) {
            WorldEditCUINetworkHandler.registerReceiver();
        }
    }

    // WorldEdit CUI storage
    public void onWorldEditConfigChange(ConfigBoolean value) {
        this.resetWorldEditPos();
        if (this.inGame) {
            if (value.getBooleanValue()) {
                WorldEditCUINetworkHandler.registerReceiver();
            } else {
                WorldEditCUINetworkHandler.unregisterReceiver();
            }
        }
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
