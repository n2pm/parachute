package pm.n2.parachute.mixin;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;

@Mixin(SharedConstants.class)
public class MixinSharedConstants {
    @Inject(method = "isValidChar", at = @At("HEAD"),cancellable = true)
    private static void isValidChar(char chr, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.TweakConfigs.ALLOW_DISALLOWED_CHARS.getBooleanValue()) {
            cir.setReturnValue(true);
        }
    }
}
