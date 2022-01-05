package pm.n2.parachute.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.config.Configs;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud_showLatencyMs {
    @Final
    @Shadow
    private
    MinecraftClient client;

    @Redirect(method="render", at=@At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I", ordinal = 0))
    private int rectSize(int currentSize, int textSize) {
        if (Configs.TweakConfigs.PLAYER_LIST_PING.getBooleanValue()) {
            // Extra zero to account for a space.
            textSize += this.client.textRenderer.getWidth("00ms");
        }
        return Math.max(currentSize, textSize);
    }

    @Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
    private void numericPing(MatrixStack matrixStack, int x, int w, int y, PlayerListEntry playerEntry, CallbackInfo ci) {
        if (Configs.TweakConfigs.PLAYER_LIST_PING.getBooleanValue()) {
            int color;
            int latency = playerEntry.getLatency();

            if (latency > 500) {
                color = 0xAA0000;
            } else if (latency > 300) {
                color = 0xAAAA00;
            } else if (latency > 200) {
                color = 0xAACC00;
            } else if (latency > 135) {
                color = 0x207B00;
            } else if (latency > 70) {
                color = 0x009900;
            } else if (latency >= 0) {
                color = 0x00BB00;
            } else {
                color = 0xAA0000;
            }

            int xPos = w + x - this.client.textRenderer.getWidth(Math.min(playerEntry.getLatency(), 999) + "ms");

            this.client.textRenderer.drawWithShadow(matrixStack, Math.min(playerEntry.getLatency(), 999) + "ms", xPos, y, color);

            ci.cancel();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isInSingleplayer()Z"))
    public boolean alwaysDrawPlayerIcons(MinecraftClient client) {
//        return true;
        return client.isInSingleplayer();
    }

}
