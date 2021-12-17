package pm.n2.parachute.mixin;

import com.mojang.authlib.minecraft.TelemetrySession;
import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.Parachute;

import java.util.concurrent.Executor;

@Mixin(YggdrasilUserApiService.class)
public class MixinYggdrasilUserApiService_authlib {
    @Inject(method = "newTelemetrySession", at = @At("HEAD"), cancellable = true, remap = false)
    private void preventTelemetrySession(Executor executor, CallbackInfoReturnable<TelemetrySession> cir) {
        Parachute.LOGGER.info("Stopping creation of telemetry session");
        cir.setReturnValue(TelemetrySession.DISABLED);
        cir.cancel();
    }
}
