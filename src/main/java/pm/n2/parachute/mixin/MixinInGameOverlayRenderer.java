package pm.n2.parachute.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.config.Configs;

@Mixin(InGameOverlayRenderer.class)
public class MixinInGameOverlayRenderer {
    @Inject(method="renderFireOverlay", at=@At(value="HEAD"), cancellable = true)
    private static void hideFireOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci){
        if (Configs.RenderConfigs.HIDE_FIRE_OVERLAY.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method="renderUnderwaterOverlay", at=@At(value="HEAD"), cancellable = true)
    private static void hideWaterOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci){
        if (Configs.RenderConfigs.HIDE_UNDERWATER_OVERLAY.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method="renderInWallOverlay", at=@At(value="HEAD"), cancellable = true)
    private static void hideWallOverlay(Sprite sprite, MatrixStack matrices, CallbackInfo ci){
        if (Configs.RenderConfigs.HIDE_INBLOCK_OVERLAY.getBooleanValue()) {
            ci.cancel();
        }
    }
}
