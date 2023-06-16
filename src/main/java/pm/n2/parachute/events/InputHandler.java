package pm.n2.parachute.events;

import com.google.common.collect.ImmutableList;
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
        var keybinds = ImmutableList.<IKeybind>builder()
                .addAll(Configs.GENERAL_CONFIGS.getKeybinds())
                .addAll(Configs.FEATURE_CONFIGS.getKeybinds())
                .addAll(Configs.TWEAK_CONFIGS.getKeybinds())
                .addAll(Configs.RENDER_CONFIGS.getKeybinds())
                .build();

        for (var keybind : keybinds) manager.addKeybindToMap(keybind);
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", Configs.RENDER_CONFIGS.getHotkeys());
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", Configs.TWEAK_CONFIGS.getHotkeys());
        manager.addHotkeysForCategory(Parachute.MOD_NAME, "parachute.hotkeys.category", Configs.RENDER_CONFIGS.getHotkeys());
    }
}
