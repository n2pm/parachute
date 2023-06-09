package pm.n2.parachute.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;

import java.net.URI;
import java.net.URISyntaxException;

@Pseudo
@Mixin(YggdrasilMinecraftSessionService.class)
public class MixinYggdrasilMinecraftSessionService_authlib {
//    @Shadow(remap = false)
//    @Final
//    private static String[] ALLOWED_DOMAINS;
//
//    @Shadow(remap = false)
//    private static boolean isDomainOnList(final String domain, final String[] list) {
//        return false;
//    }
//
//    @Inject(method = "isAllowedTextureDomain", at = @At(value = "HEAD"), cancellable = true, remap = false)
//    private static void ignoreTextureDomainCheck(String url, CallbackInfoReturnable<Boolean> cir) {
//        if (Configs.TweakConfigs.SKIN_SIDELOADING_ENABLED.getBooleanValue()) {
//            // Copy vanilla code for getting the domain since it's Not In Scope
//            URI uri;
//
//            try {
//                uri = new URI(url);
//            } catch (final URISyntaxException ignored) {
//                throw new IllegalArgumentException("Invalid URL '" + url + "'");
//            }
//
//            final String domain = uri.getHost();
//
//            if (Configs.TweakConfigs.SKIN_SIDELOADING_NON_MOJANG_DOMAINS.getBooleanValue()) {
//                cir.setReturnValue(true);
//            } else {
//                cir.setReturnValue(isDomainOnList(domain, ALLOWED_DOMAINS));
//            }
//            cir.cancel();
//        }
//    }
}
