package pm.n2.parachute.mixin;

import pm.n2.parachute.config.RenderConfigs;
import pm.n2.parachute.config.TweakConfigs;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "hasReducedDebugInfo", at = @At("HEAD"), cancellable = true)
    private void forceShowDebugInfo(CallbackInfoReturnable<Boolean> cir) {
        if (TweakConfigs.TWEAK_FORCE_DETAILED_DEBUG_INFO.getBooleanValue()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method="hasOutline", at=@At("HEAD"), cancellable = true)
    private void forceShowOutline(CallbackInfoReturnable<Boolean> cir) {
        if (RenderConfigs.RENDER_FORCE_GLOWING.getBooleanValue()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
