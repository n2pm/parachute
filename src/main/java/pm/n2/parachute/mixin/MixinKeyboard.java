package pm.n2.parachute.mixin;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pm.n2.parachute.config.Configs;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @ModifyArgs(method = "processDebugKeys", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Keyboard;copyLocationFromTarget(ZZ)V"))
    private void copyLookAt(Args args) {
        boolean hasQueryPermission = args.get(0);
        boolean tweakEnabled = Configs.TweakConfigs.FORCE_COPY_DEBUG_INFO.getBooleanValue();

        if (tweakEnabled && !hasQueryPermission) {
            args.set(0, true);
            args.set(1, false);
        }
    }
}
