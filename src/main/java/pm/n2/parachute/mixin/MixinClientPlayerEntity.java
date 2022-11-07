package pm.n2.parachute.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.config.Configs;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Shadow
    @Final
    protected MinecraftClient client;
    private double lastPosX = Double.MAX_VALUE;
    private double lastPosY;
    private double lastPosZ;

    private double lastTpX;
    private double lastTpY;
    private double lastTpZ;

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isCamera()Z"))
    private boolean modifyMovement(ClientPlayerEntity instance) {
        Vec3d pos = ((IMixinEntity) instance).getPos();
        if (Configs.TweakConfigs.LIVEOVERFLOW_WORLDGUARD_BYPASS.getBooleanValue()) {
            if (this.lastPosX == Double.MAX_VALUE) {
                // Initialize
                // TODO: Doesn't work on first join or reconnect for some reason
                this.lastPosX = pos.x;
                this.lastPosY = pos.y;
                this.lastPosZ = pos.z;
                this.lastTpX = pos.x;
                this.lastTpY = pos.y;
                this.lastTpZ = pos.z;
            }

            // Slow all movements
            double newX = this.lastPosX + (pos.x - this.lastPosX) * 0.03d;
            double newY = this.lastPosY + (pos.y - this.lastPosY) * 0.03d;
            double newZ = this.lastPosZ + (pos.z - this.lastPosZ) * 0.03d;
            instance.setPosition(newX, newY, newZ);

            double delta = Math.pow(this.lastTpX - newX, 2) + Math.pow(this.lastTpY - newY, 2) + Math.pow(this.lastTpZ - newZ, 2);
            if (delta > 1 / 300f) {
                this.lastTpX = newX;
                this.lastTpY = newY;
                this.lastTpZ = newZ;
                instance.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, -128, pos.z, instance.isOnGround()));
            }

            this.lastPosX = newX;
            this.lastPosY = newY;
            this.lastPosZ = newZ;
        } else {
            // Keep updated when not enabled
            this.lastPosX = pos.x;
            this.lastPosY = pos.y;
            this.lastPosZ = pos.z;
            this.lastTpX = pos.x;
            this.lastTpY = pos.y;
            this.lastTpZ = pos.z;
        }
        return ((IMixinClientPlayerEntity) instance).invokeIsCamera();
    }

    @Redirect(method = "updateNausea", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;closeHandledScreen()V", ordinal = 0))
    private void allowGuisInPortal_1(ClientPlayerEntity player) {
        if (!Configs.FeatureConfigs.PORTAL_SCREENS.getBooleanValue()) {
            player.closeHandledScreen();
        }
    }

    @Redirect(method = "updateNausea", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", ordinal = 0))
    private void allowGuisInPortal_2(MinecraftClient instance, Screen screen) {
        if (!Configs.FeatureConfigs.PORTAL_SCREENS.getBooleanValue()) {
            instance.setScreen(screen);
        }
    }
}
