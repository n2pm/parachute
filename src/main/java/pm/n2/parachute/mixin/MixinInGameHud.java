package pm.n2.parachute.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.config.FeatureOverride;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    @Final
    @Shadow
    private static Identifier PUMPKIN_BLUR;

    @Final
    @Shadow
    private static Identifier POWDER_SNOW_OUTLINE;

    // TODO: get rid of the whitespace that remains
    @ModifyVariable(method = "renderScoreboardSidebar", at = @At(value = "STORE", target = "Lnet/minecraft/scoreboard/ScoreboardPlayerScore;getScore()I"))
    private String hideScoreboardNumbers(String original) {
        boolean tweakEnabled = Configs.RenderConfigs.HIDE_SCOREBOARD_NUMBERS.getBooleanValue();
        return tweakEnabled ? "" : original;
    }

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void hideScoreboard(MatrixStack matrices, ScoreboardObjective objective, CallbackInfo ci) {
        if (Configs.RenderConfigs.HIDE_SCOREBOARD.getBooleanValue()) ci.cancel();
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void hideCrosshair(MatrixStack matrices, CallbackInfo ci) {
        if (Configs.RenderConfigs.SHOW_CROSSHAIR.getOptionListValue() == FeatureOverride.FORCE_FALSE) {
            ci.cancel();
        }
    }

    @Inject(method = "shouldRenderSpectatorCrosshair", at = @At("HEAD"), cancellable = true)
    private void showCrosshair(HitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.RenderConfigs.SHOW_CROSSHAIR.getOptionListValue() == FeatureOverride.FORCE_TRUE) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void hideSpyglass(float scale, CallbackInfo ci) {
        if (Configs.RenderConfigs.HIDE_SPYGLASS_OVERLAY.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private void hideGenericOverlay(Identifier texture, float opacity, CallbackInfo ci) {
        if (Configs.RenderConfigs.HIDE_PUMPKIN_OVERLAY.getBooleanValue() && texture == PUMPKIN_BLUR) {
            ci.cancel();
        }
        if (Configs.RenderConfigs.HIDE_POWDER_SNOW_OVERLAY.getBooleanValue() && texture == POWDER_SNOW_OUTLINE) {
            ci.cancel();
        }
    }

    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    private void hideVignette(Entity entity, CallbackInfo ci) {
        if (Configs.RenderConfigs.HIDE_VIGNETTE.getBooleanValue()) {
            ci.cancel();
        }
    }
}
