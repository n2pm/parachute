package pm.n2.parachute.mixin;

import net.minecraft.text.LiteralText;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.util.GlobalDataStorage;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class MixinDisconnectedScreen extends Screen {
    @Shadow
    private int reasonHeight;
    @Unique
    private ButtonWidget reconnectBtn;
    @Unique
    private int time = Configs.FeatureConfigs.AUTO_RECONNECT_TIME.getIntegerValue() * 20;

    protected MixinDisconnectedScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addReconnectButton(CallbackInfo info) {
        ServerInfo lastServerInfo = GlobalDataStorage.getInstance().getLastServer();
        boolean tweakEnabled = Configs.FeatureConfigs.RECONNECT_BUTTON.getBooleanValue();

        if (lastServerInfo != null && tweakEnabled) {
            int x = width / 2 - 100;
            int y = Math.min((height / 2 + reasonHeight / 2) + 32, height - 30);

            reconnectBtn = addDrawableChild(new ButtonWidget(x, y, 200, 20, getText(), button -> reconnect(lastServerInfo)));
        }
    }

    @Override
    public void tick() {
        ServerInfo lastServerInfo = GlobalDataStorage.getInstance().getLastServer();
        boolean tweakEnabled = Configs.FeatureConfigs.AUTO_RECONNECT_ENABLED.getBooleanValue();

        if (lastServerInfo != null && tweakEnabled) {
            if (time <= 0) {
                reconnect(lastServerInfo);
            } else {
                time--;
                if (reconnectBtn != null) {
                    ((IMixinClickableWidget) reconnectBtn).setText(getText());
                }
            }
        }
    }

    private void reconnect(ServerInfo lastServerInfo) {
        Parachute.LOGGER.info("Reconnecting...");
        assert client != null;
        ConnectScreen.connect(new MultiplayerScreen(new TitleScreen()), client, ServerAddress.parse(lastServerInfo.address), lastServerInfo);
    }

    private LiteralText getText() {
        String reconnectText = "Reconnect";
        float timeLeft = (float) time / 20;

        boolean autoReconnect = Configs.FeatureConfigs.AUTO_RECONNECT_ENABLED.getBooleanValue();
        if (autoReconnect) reconnectText += " " + String.format("(%.1f)", timeLeft);

        return new LiteralText(reconnectText);
    }
}
