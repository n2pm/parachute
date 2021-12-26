package pm.n2.parachute.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import pm.n2.parachute.config.Configs;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud {
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0), index = 1)
    private int ignorePlayerListSizeLimit(int count) {
        if (Configs.TweakConfigs.CUSTOM_PLAYER_LIST_LENGTH_ENABLED.getBooleanValue()) {
            return Configs.TweakConfigs.CUSTOM_PLAYER_LIST_LENGTH.getIntegerValue();
        }
        return count;
    }
}
