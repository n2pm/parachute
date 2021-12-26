package pm.n2.parachute.events;

import fi.dy.masa.malilib.hotkeys.*;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.config.Configs;

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
        for (IKeybind keybind : Configs.GENERAL_CONFIGS.getKeybinds()) {
            manager.addKeybindToMap(keybind);
        }
        for (IKeybind keybind : Configs.TWEAK_CONFIGS.getKeybinds()) {
            manager.addKeybindToMap(keybind);
        }
        for (IKeybind keybind : Configs.RENDER_CONFIGS.getKeybinds()) {
            manager.addKeybindToMap(keybind);
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", Configs.RENDER_CONFIGS.getHotkeys());
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", Configs.TWEAK_CONFIGS.getHotkeys());
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", Configs.RENDER_CONFIGS.getHotkeys());
    }
}
