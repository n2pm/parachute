package pm.n2.parachute.mixin;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.util.GlobalDataStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.InetSocketAddress;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "connect", at = @At("HEAD"))
    private static void connect(InetSocketAddress address, boolean useEpoll, CallbackInfoReturnable<ClientConnection> info) {
        MinecraftClient mc = MinecraftClient.getInstance();
        GlobalDataStorage.getInstance().setLastServer(mc.isInSingleplayer() ? null : mc.getCurrentServerEntry());
    }

    @Inject(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at=@At("HEAD"))
    private void send(Packet<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo ci) {
        if (Configs.TweakConfigs.LIVEOVERFLOW_BOT_MOVEMENT.getBooleanValue() && packet instanceof PlayerMoveC2SPacket && ((PlayerMoveC2SPacket) packet).changesPosition()) {
            System.out.println("Intercepted");
            ((IMixinPlayerMoveC2SPacket) packet).setX(Math.floor(((PlayerMoveC2SPacket) packet).getX(0) * 100)/100);
            ((IMixinPlayerMoveC2SPacket) packet).setZ(Math.floor(((PlayerMoveC2SPacket) packet).getZ(0) * 100)/100);
            System.out.println(((PlayerMoveC2SPacket) packet).getX(0));
            System.out.println(((PlayerMoveC2SPacket) packet).getZ(0));
        }
    }
}
