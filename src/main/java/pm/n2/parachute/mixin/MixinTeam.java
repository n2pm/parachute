package pm.n2.parachute.mixin;

import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.config.FeatureOverride;

@Mixin(Team.class)
public class MixinTeam {
    @Inject(method = "getNameTagVisibilityRule", at = @At("HEAD"), cancellable = true)
    public void getNameTagVisibilityRule(CallbackInfoReturnable<AbstractTeam.VisibilityRule> cir) {
        switch ((FeatureOverride) Configs.TweakConfigs.SHOW_NAMETAGS.getOptionListValue()) {
            case DEFAULT:
                return;
            case FORCE_FALSE:
                cir.setReturnValue(AbstractTeam.VisibilityRule.NEVER);
                return;
            case FORCE_TRUE:
                cir.setReturnValue(AbstractTeam.VisibilityRule.ALWAYS);
        }
    }
}
