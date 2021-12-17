package pm.n2.parachute.events;

import pm.n2.parachute.Parachute;
import pm.n2.parachute.config.GenericConfigs;
import pm.n2.parachute.config.RenderConfigs;
import pm.n2.parachute.config.TweakConfigs;
import fi.dy.masa.malilib.hotkeys.*;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler {
    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() {
        super();
    }


    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (IHotkey hotkey : GenericConfigs.HOTKEY_LIST) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
        for (IHotkey hotkey : TweakConfigs.OPTIONS) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
        for (IHotkey hotkey : RenderConfigs.OPTIONS) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", GenericConfigs.HOTKEY_LIST);
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", TweakConfigs.OPTIONS);
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", RenderConfigs.OPTIONS);
    }
}
