package pm.n2.parachute.events;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.MinecraftClient;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.gui.ConfigGui;
import pm.n2.parachute.impulses.SwapHotbar;

public class KeyCallbacks {
    public static void init(MinecraftClient client) {
        IHotkeyCallback callbackGeneric = new KeyCallbackHotkeysGeneric(client);

        Configs.GeneralConfigs.OPEN_CONFIG_GUI.getKeybind().setCallback(callbackGeneric);
        Configs.GeneralConfigs.OPEN_CLIENT_COMMANDS.getKeybind().setCallback(callbackGeneric);
        Configs.FeatureConfigs.SWAP_HOTBAR.getKeybind().setCallback(callbackGeneric);
    }

    private static class KeyCallbackHotkeysGeneric implements IHotkeyCallback {
        private static MinecraftClient client;
        public KeyCallbackHotkeysGeneric(MinecraftClient client2) {
            client = client2;
        }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            if (key == Configs.GeneralConfigs.OPEN_CONFIG_GUI.getKeybind()) {
                GuiBase.openGui(new ConfigGui());
                return true;
            }

            if (key == Configs.FeatureConfigs.SWAP_HOTBAR.getKeybind()) {
                SwapHotbar.run();
                return true;
            }

            return false;
        }
    }
}