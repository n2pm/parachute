package pm.n2.parachute.mixin;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.config.Configs;

@Mixin(PlayerMoveC2SPacket.class)
public class MixinPlayerMoveC2SPacket {
    @Final
    @Mutable
    @Shadow
    protected double x;

    @Final
    @Mutable
    @Shadow
    protected double z;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void fuck(double x, double y, double z, float pitch, float yaw, boolean bl, boolean changePosition, boolean bl3, CallbackInfo ci) {
        if (changePosition) {
            if (Configs.TweakConfigs.LIVEOVERFLOW_BOT_MOVEMENT.getBooleanValue()) {
                this.x = Math.floor(this.x * 100) / 100;
                this.z = Math.floor(this.z * 100) / 100;
            }
        }
    }
}
