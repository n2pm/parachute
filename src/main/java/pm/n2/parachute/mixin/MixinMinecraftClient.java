package pm.n2.parachute.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "hasReducedDebugInfo", at = @At("HEAD"), cancellable = true)
    private void forceShowDebugInfo(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.TweakConfigs.FORCE_DETAILED_DEBUG_INFO.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void forceShowOutline(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.RenderConfigs.FORCE_GLOWING.getBooleanValue()) {
            cir.setReturnValue(true);
        }
    }

    // TODO: Can we not redirect?
    @Redirect(method = "drawProfilerResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Matrix4f;projectionMatrix(FFFFFF)Lnet/minecraft/util/math/Matrix4f;"))
    private Matrix4f changeProfilerScale(float left, float right, float bottom, float top, float nearPlane, float farPlane) {
        if (Configs.RenderConfigs.SCALE_DEBUG_PIE.getBooleanValue()) {
            MinecraftClient client = (MinecraftClient) (Object) this;
            Window window = client.getWindow();
            return Matrix4f.projectionMatrix(left, (float) ((double) window.getFramebufferWidth() / (window.getScaleFactor() / 2)), bottom, (float) ((double) window.getFramebufferHeight() / (window.getScaleFactor() / 2)), nearPlane, farPlane);
        }
        return Matrix4f.projectionMatrix(left, right, bottom, top, nearPlane, farPlane);
    }

    @Redirect(method = "drawProfilerResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getFramebufferHeight()I"))
    private int changeProfilerScaleHeight(Window instance) {
        if (Configs.RenderConfigs.SCALE_DEBUG_PIE.getBooleanValue()) {
            return (int) (instance.getFramebufferHeight() / (instance.getScaleFactor() / 2));
        }
        return instance.getFramebufferHeight();
    }

    @Redirect(method = "drawProfilerResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getFramebufferWidth()I"))
    private int changeProfilerScaleWidth(Window instance) {
        if (Configs.RenderConfigs.SCALE_DEBUG_PIE.getBooleanValue()) {
            return (int) (instance.getFramebufferWidth() / (instance.getScaleFactor() / 2));
        }
        return instance.getFramebufferWidth();
    }
}
