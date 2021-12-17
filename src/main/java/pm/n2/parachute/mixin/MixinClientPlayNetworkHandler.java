package pm.n2.parachute.mixin;

import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.config.TweakConfigs;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.s2c.play.ResourcePackSendS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Shadow
    @Final
    private ClientConnection connection;

    @Inject(method = "onResourcePackSend", at = @At("HEAD"), cancellable = true)
    private void onResourcePackSend(ResourcePackSendS2CPacket packet, CallbackInfo ci) {
        boolean tweakEnabled = TweakConfigs.TWEAK_IGNORE_RESOURCE_PACKS.getBooleanValue();
        if (tweakEnabled) {
            this.connection.send(new ResourcePackStatusC2SPacket(ResourcePackStatusC2SPacket.Status.ACCEPTED));
            this.connection.send(new ResourcePackStatusC2SPacket(ResourcePackStatusC2SPacket.Status.SUCCESSFULLY_LOADED));
            ci.cancel();
        }
    }

    @Inject(method = "onCustomPayload", at=@At("HEAD"))
    private void onCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        if (TweakConfigs.TWEAK_DEBUG_CUSTOM_CHANNELS.getBooleanValue()) {
            Parachute.LOGGER.info("onCustomPayload: received packet on channel {}", packet.getChannel());
        }
    }
}
