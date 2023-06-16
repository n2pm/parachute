package pm.n2.parachute.impulses;

import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;

public class SwapHotbar {
    public static void run() {
        var mc = MinecraftClient.getInstance();

        var player = mc.player;
        if (player == null) return;

        var interactionManager = mc.interactionManager;
        if (interactionManager == null) return;

        var syncId = player.currentScreenHandler != null
                ? player.currentScreenHandler.syncId
                : 0;

        for (var i = 0; i < 9; i++) {
            interactionManager.clickSlot(
                    syncId,
                    i + 27,
                    i,
                    SlotActionType.SWAP,
                    player
            );
        }
    }
}
