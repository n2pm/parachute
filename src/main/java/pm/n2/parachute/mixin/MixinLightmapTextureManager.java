package pm.n2.parachute.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.config.Configs;

@Mixin(LightmapTextureManager.class)
public abstract class MixinLightmapTextureManager {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private boolean forceNightVision(ClientPlayerEntity instance, StatusEffect statusEffect) {
        if (Configs.RenderConfigs.NIGHT_VISION.getBooleanValue()) {
            return true;
        }
        return instance.hasStatusEffect(statusEffect);
    }

    @Redirect(method = "update", at=@At(value="INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getNightVisionStrength(Lnet/minecraft/entity/LivingEntity;F)F"), require = 0)
    private float forceNightVisionDuration(LivingEntity entity, float f) {
        if (Configs.RenderConfigs.NIGHT_VISION.getBooleanValue()) {
            return 1.0F;
        }

        return GameRenderer.getNightVisionStrength(entity, f);
    }
}
