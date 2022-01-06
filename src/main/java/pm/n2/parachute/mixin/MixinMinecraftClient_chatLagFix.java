package pm.n2.parachute.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;

import java.util.UUID;

@Mixin(value = MinecraftClient.class, priority = 999)
public class MixinMinecraftClient_chatLagFix {
    @Inject(method = "shouldBlockMessages", at = @At("HEAD"), cancellable = true)
    private void chatLagFix(UUID uuid, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.BugFixConfigs.CHAT_LAG_FIX.getBooleanValue()) {
            cir.setReturnValue(false);
            return;
        }
        // Parachute.LOGGER.info("You should see a lag spike about now.");
    }
}
