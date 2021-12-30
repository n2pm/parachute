package pm.n2.parachute.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import pm.n2.parachute.config.Configs;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public class MixinMultiplayerServerListWidget_ServerEntry {
    @Final
    @Shadow
    private
    ServerInfo server;

    // TODO: Make this less prone to
    // Could also move to a redirect on textRenderer
    @ModifyVariable(method = "render", at = @At(value = "STORE"), index = 13)
    private Text detailedVersionInfo(Text original) {
        if (Configs.TweakConfigs.MULTIPLAYER_SCREEN_DETAILED_VERSION_INFO.getBooleanValue()) {
            if (this.server.online && this.server.ping != -2L) {
                if (this.server.ping < 0L) {
                    return new LiteralText("Offline").formatted(Formatting.DARK_RED);
                }
                boolean bl = this.server.protocolVersion != SharedConstants.getGameVersion().getProtocolVersion();
                return new LiteralText("")
                        .append(this.server.version.shallowCopy().formatted(bl ? Formatting.RED : Formatting.GRAY))
                        .append(new LiteralText(" (").formatted(Formatting.DARK_GRAY))
                        .append(new LiteralText(this.server.protocolVersion + "").formatted(Formatting.GRAY))
                        .append(new LiteralText(") ").formatted(Formatting.DARK_GRAY))
                        .append(this.server.playerCountLabel);
            } else {
                return new LiteralText("Pinging...");
            }

        }
        return original;
    }
}
