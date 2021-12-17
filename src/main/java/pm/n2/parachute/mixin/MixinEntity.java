package pm.n2.parachute.mixin;

import pm.n2.parachute.config.TweakConfigs;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {
    @Inject(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At("HEAD"))
    private void setStepHeight(Vec3d movement, CallbackInfoReturnable<Vec3d> cir) {
        Entity entity = (Entity) (Object) this;
        if (entity.isPlayer()) {
            // 0.6F defined in constructor of LivingEntity.java
            entity.stepHeight = TweakConfigs.TWEAK_STEP_ASSIST.getBooleanValue() ? 1.6F : 0.6F;
        }
    }
}
