package pm.n2.parachute.mixin;

import pm.n2.parachute.config.RenderConfigs;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class MixinBossBarHud {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, CallbackInfo ci) {
        boolean tweakEnabled = RenderConfigs.RENDER_HIDE_BOSSBAR.getBooleanValue();
        if (tweakEnabled) ci.cancel();
    }
}
