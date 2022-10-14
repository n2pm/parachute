package pm.n2.parachute.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.config.Configs;

@Mixin(ChatHud.class)
public class MixinChatHud {
    // dawg this shit is Not Mapped :sob:
    @ModifyConstant(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", constant = @Constant(intValue = 100), require = 2)
    public int overwriteMaxHistory(int maxLength) {
        if (Configs.TweakConfigs.CUSTOM_CHAT_HISTORY_LENGTH_ENABLED.getBooleanValue()) {
            return Configs.TweakConfigs.CUSTOM_CHAT_HISTORY_LENGTH.getIntegerValue();
        }
        return maxLength;
    }

    @Inject(method = "clear", at = @At("HEAD"), cancellable = true)
    public void clear(boolean clearHistory, CallbackInfo ci) {
        if (Configs.TweakConfigs.DONT_RESET_CHAT_HISTORY.getBooleanValue()) {
            ci.cancel();
        }
    }
}
