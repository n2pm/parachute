package pm.n2.parachute.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.config.Configs;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient client;

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
