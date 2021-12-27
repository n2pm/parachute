package pm.n2.parachute.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient_windowTitle {

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void getWindowTitle(CallbackInfoReturnable<String> cir) {
        if (Configs.TweakConfigs.TITLE_BAR_CUSTOMIZATION_ENABLED.getBooleanValue()) {
            // Partially ported from blanket
            MinecraftClient client = (MinecraftClient) (Object) this;
            StringBuilder stringBuilder = new StringBuilder("Minecraft");
            if (MinecraftClient.getModStatus().isModded() && !Configs.TweakConfigs.TITLE_BAR_HIDE_MODDED.getBooleanValue()) {
                stringBuilder.append("*");
            }
            stringBuilder.append(" ");
            stringBuilder.append(SharedConstants.getGameVersion().getName());
            if (!Configs.TweakConfigs.TITLE_BAR_HIDE_GAME_STATUS.getBooleanValue()) {
                ClientPlayNetworkHandler clientPlayNetworkHandler = client.getNetworkHandler();
                if (clientPlayNetworkHandler != null && clientPlayNetworkHandler.getConnection().isOpen()) {
                    stringBuilder.append(" - ");
                    if (client.getServer() != null && !client.getServer().isRemote()) {
                        stringBuilder.append(I18n.translate("title.singleplayer"));
                    } else if (client.isConnectedToRealms()) {
                        stringBuilder.append(I18n.translate("title.multiplayer.realms"));
                    } else if (client.getServer() != null || client.getCurrentServerEntry() != null && client.getCurrentServerEntry().isLocal()) {
                        stringBuilder.append(I18n.translate("title.multiplayer.lan"));
                    } else {
                        stringBuilder.append(I18n.translate("title.multiplayer.other"));
                    }
                }
            }
            cir.setReturnValue(stringBuilder.toString());
        }
    }
}
