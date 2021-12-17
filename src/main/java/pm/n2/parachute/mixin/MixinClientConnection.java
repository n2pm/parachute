package pm.n2.parachute.mixin;

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
}
