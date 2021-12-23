package pm.n2.parachute.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.ParachuteCommands;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Final
    @Shadow
    protected MinecraftClient client;

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(String message, CallbackInfo ci) {
        if (!ParachuteCommands.getInstance().executeCommand(message)) {
            ci.cancel();
        }
    }
}
