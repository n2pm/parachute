package pm.n2.parachute.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pm.n2.parachute.config.Configs;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Redirect(method = "updateNausea", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;closeHandledScreen()V", ordinal = 0))
    private void allowGuisInPortal_1(ClientPlayerEntity player) {
        if (!Configs.FeatureConfigs.PORTAL_SCREENS.getBooleanValue()) {
            player.closeHandledScreen();
        }
    }

    @Redirect(method = "updateNausea", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", ordinal = 0))
    private void allowGuisInPortal_2(MinecraftClient instance, Screen screen) {
        if (!Configs.FeatureConfigs.PORTAL_SCREENS.getBooleanValue()) {
            instance.setScreen(screen);
        }
    }
}
