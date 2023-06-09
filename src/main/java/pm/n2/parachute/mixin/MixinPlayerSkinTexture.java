package pm.n2.parachute.mixin;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.PlayerSkinTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pm.n2.parachute.config.Configs;

@Mixin(PlayerSkinTexture.class)
public class MixinPlayerSkinTexture {
//    @Redirect(method = "remapTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/NativeImage;getHeight()I"))
//    private int spoofHeight(NativeImage instance) {
//        int height = instance.getHeight();
//        if (Configs.TweakConfigs.SKIN_SIDELOADING_ENABLED.getBooleanValue()) {
//            return (height != 64 && height != 32) ? 64 : height;
//        }
//        return height;
//    }
//
//    @Redirect(method = "remapTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/NativeImage;getWidth()I"))
//    private int spoofWidth(NativeImage instance) {
//        return Configs.TweakConfigs.SKIN_SIDELOADING_ENABLED.getBooleanValue() ? 64 : instance.getWidth();
//    }
}
