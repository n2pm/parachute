package pm.n2.parachute.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.scoreboard.AbstractTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.config.FeatureOverride;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(method = "shouldRenderBlockOutline", at = @At(value = "HEAD"), cancellable = true)
    private void alwaysShowBlockOutline(CallbackInfoReturnable<Boolean> cir) {
        switch ((FeatureOverride) Configs.RenderConfigs.SHOW_BLOCK_OUTLINE.getOptionListValue()) {
            case DEFAULT:
                return;
            case FORCE_FALSE:
                cir.setReturnValue(false);
                return;
            case FORCE_TRUE:
                cir.setReturnValue(true);
        }
    }
}
