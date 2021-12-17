package pm.n2.parachute.events;

import pm.n2.parachute.config.GenericConfigs;
import pm.n2.parachute.gui.ConfigGui;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.MinecraftClient;

public class KeyCallbacks {
    public static void init(MinecraftClient client) {
        IHotkeyCallback callbackGeneric = new KeyCallbackHotkeysGeneric(client);

        GenericConfigs.OPEN_CONFIG_GUI.getKeybind().setCallback(callbackGeneric);
    }

    private static class KeyCallbackHotkeysGeneric implements IHotkeyCallback {

        public KeyCallbackHotkeysGeneric(MinecraftClient client) {
        }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            if (key == GenericConfigs.OPEN_CONFIG_GUI.getKeybind()) {
                GuiBase.openGui(new ConfigGui());
                return true;
            }
            return false;
        }
    }
}