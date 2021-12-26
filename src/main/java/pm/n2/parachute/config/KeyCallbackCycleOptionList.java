package pm.n2.parachute.config;

import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;

public class KeyCallbackCycleOptionList implements IHotkeyCallback {
    protected final ConfigOptionListHotkeyed config;

    public KeyCallbackCycleOptionList(ConfigOptionListHotkeyed config) {
        this.config = config;
    }

    @Override
    public boolean onKeyAction(KeyAction keyAction, IKeybind iKeybind) {
        this.config.setOptionListValue(this.config.getOptionListValue().cycle(true));
        return true;
    }
}
