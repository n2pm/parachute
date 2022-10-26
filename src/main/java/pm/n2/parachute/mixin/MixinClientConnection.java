package pm.n2.parachute.mixin;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketSendListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.util.GlobalDataStorage;

import java.net.InetSocketAddress;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "connect", at = @At("HEAD"))
    private static void connect(InetSocketAddress address, boolean useEpoll, CallbackInfoReturnable<ClientConnection> info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        GlobalDataStorage.getInstance().setLastServer(mc.isInSingleplayer() ? null : mc.getCurrentServerEntry());
    }

    @Inject(method = "send(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketSendListener;)V", at = @At("HEAD"))
    private void send(Packet<?> packet, PacketSendListener listener, CallbackInfo ci) {
        // Packet interception entry point
    }
}
