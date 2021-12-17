package pm.n2.parachute.mixin;

import pm.n2.parachute.config.RenderConfigs;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {
    @Redirect(method = "applyFog", at=@At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private static boolean disableBlindness(LivingEntity instance, StatusEffect effect) {
        if (RenderConfigs.RENDER_NO_BLINDNESS.getBooleanValue() && effect == StatusEffects.BLINDNESS) {
            return false;
        }
        return instance.hasStatusEffect(effect);
    }
}
