package pm.n2.parachute.mixin;

import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;

@Mixin(Camera.class)
public class MixinCamera {
    @Inject(method = "clipToSpace", at = @At(value = "HEAD"), cancellable = true)
    private void thirdPersonCameraNoClip(double desiredCameraDistance, CallbackInfoReturnable<Double> cir) {
        if (Configs.TweakConfigs.THIRD_PERSON_CAMERA_NO_CLIP.getBooleanValue()) {
            cir.setReturnValue(desiredCameraDistance);
            cir.cancel();
        }
    }
}
