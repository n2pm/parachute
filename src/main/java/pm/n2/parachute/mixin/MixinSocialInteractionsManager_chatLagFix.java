package pm.n2.parachute.mixin;

import net.minecraft.client.network.SocialInteractionsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;

import java.util.UUID;

@Mixin(SocialInteractionsManager.class)
public class MixinSocialInteractionsManager_chatLagFix {
    @Inject(method = "isPlayerMuted", at = @At("HEAD"), cancellable = true)
    private void chatLagFix(UUID uuid, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.BugFixConfigs.CHAT_LAG_FIX.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }
}
