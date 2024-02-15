package pm.n2.parachute.mixin;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.util.GlobalDataStorage;

@Mixin(DisconnectedScreen.class)
public abstract class MixinDisconnectedScreen extends Screen {
    @Shadow
    @Final
    private DirectionalLayoutWidget grid;

    @Unique
    private ButtonWidget reconnectBtn;
    @Unique
    private int time = Configs.FeatureConfigs.AUTO_RECONNECT_TIME.getIntegerValue() * 20;

    protected MixinDisconnectedScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;refreshPositions()V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void addReconnectButton(CallbackInfo ci, ButtonWidget buttonWidget) {
        ServerInfo lastServerInfo = GlobalDataStorage.getInstance().getLastServer();
        boolean tweakEnabled = Configs.FeatureConfigs.RECONNECT_BUTTON.getBooleanValue();
        System.out.println(lastServerInfo);
        if (lastServerInfo != null && tweakEnabled) {
            System.out.println("Adding to grid");
            this.reconnectBtn = ButtonWidget.builder(getText(), button -> reconnect(lastServerInfo)).build();
            this.grid.add(this.reconnectBtn);
        }
        this.grid.refreshPositions();
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
