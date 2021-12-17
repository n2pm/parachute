package pm.n2.parachute.mixin;

import pm.n2.parachute.config.RenderConfigs;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(method = "shouldRenderBlockOutline", at = @At(value = "HEAD"), cancellable = true)
    private void alwaysShowBlockOutline(CallbackInfoReturnable<Boolean> cir) {
        if (RenderConfigs.RENDER_FORCE_BLOCK_OUTLINE.getBooleanValue()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "getNightVisionStrength", at = @At(value = "HEAD"), cancellable = true)
    private static void disableNightVisionFlashing(LivingEntity entity, float f, CallbackInfoReturnable<Float> cir) {
        if (RenderConfigs.RENDER_NO_NIGHT_VISION_FLASHING.getBooleanValue()) {
            cir.setReturnValue(1.0F);
            cir.cancel();
        }
    }
}
