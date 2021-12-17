package pm.n2.parachute.mixin;

import pm.n2.parachute.config.TweakConfigs;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Team.class)
public class MixinTeam {
    @Inject(method = "getNameTagVisibilityRule", at = @At("HEAD"), cancellable = true)
    public void getNameTagVisibilityRule(CallbackInfoReturnable<AbstractTeam.VisibilityRule> cir) {
        if (TweakConfigs.TWEAK_FORCE_SHOW_NAMETAG.getBooleanValue()) {
            cir.setReturnValue(AbstractTeam.VisibilityRule.ALWAYS);
            cir.cancel();
        }
    }
}
