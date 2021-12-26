package pm.n2.parachute.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import pm.n2.parachute.config.Configs;

@Mixin(ChatHud.class)
public class MixinChatHud {
    @ModifyConstant(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", constant = @Constant(intValue = 100), require = 2)
    public int overwriteMaxHistory(int maxLength) {
        if (Configs.TweakConfigs.CUSTOM_CHAT_HISTORY_LENGTH_ENABLED.getBooleanValue()) {
            return Configs.TweakConfigs.CUSTOM_CHAT_HISTORY_LENGTH.getIntegerValue();
        }
        return maxLength;
    }
}
