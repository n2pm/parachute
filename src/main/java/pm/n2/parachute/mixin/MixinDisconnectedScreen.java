package pm.n2.parachute.mixin;

import net.minecraft.client.gui.widget.GridWidget;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DisconnectedScreen.class)
public abstract class MixinDisconnectedScreen extends Screen {
    @Unique
    private ButtonWidget reconnectBtn;
    @Unique
    private int time = Configs.FeatureConfigs.AUTO_RECONNECT_TIME.getIntegerValue() * 20;

    protected MixinDisconnectedScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void addReconnectButton(CallbackInfo ci, GridWidget.Adder adder, ButtonWidget buttonWidget) {
        ServerInfo lastServerInfo = GlobalDataStorage.getInstance().getLastServer();
        boolean tweakEnabled = Configs.FeatureConfigs.RECONNECT_BUTTON.getBooleanValue();
        if (lastServerInfo != null && tweakEnabled) {
            this.reconnectBtn = ButtonWidget.builder(getText(), button -> reconnect(lastServerInfo)).build();
            adder.add(this.reconnectBtn);
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
        ConnectScreen.connect(new MultiplayerScreen(new TitleScreen()), client, ServerAddress.parse(lastServerInfo.address), lastServerInfo, false);
    }

    private Text getText() {
        String reconnectText = "Reconnect";
        float timeLeft = (float) time / 20;

        boolean autoReconnect = Configs.FeatureConfigs.AUTO_RECONNECT_ENABLED.getBooleanValue();
        if (autoReconnect) reconnectText += " " + String.format("(%.1f)", timeLeft);

        return Text.literal(reconnectText);
    }
}
