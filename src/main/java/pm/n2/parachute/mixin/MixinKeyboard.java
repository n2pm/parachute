package pm.n2.parachute.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pm.n2.parachute.config.Configs;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Redirect(method = "processF3", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasReducedDebugInfo()Z"))
    private boolean hasReducedDebugInfo(ClientPlayerEntity instance) {
        return Configs.TweakConfigs.FORCE_COPY_DEBUG_INFO.getBooleanValue()
                ? false
                : instance.hasReducedDebugInfo();
    }

    @Redirect(method = "processF3", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasPermissionLevel(I)Z", ordinal = 0))
    private boolean copyLookAt(ClientPlayerEntity instance, int i) {
        if (!Configs.TweakConfigs.FORCE_COPY_DEBUG_INFO.getBooleanValue()) {
            return instance.hasPermissionLevel(i);
        }
        return true;
    }
}
