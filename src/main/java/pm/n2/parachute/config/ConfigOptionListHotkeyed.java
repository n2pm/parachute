package pm.n2.parachute.config;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class ConfigOptionListHotkeyed extends ConfigOptionList implements IOptionListHotkeyed {
    protected final IKeybind keybind;

    public ConfigOptionListHotkeyed(String name, IConfigOptionListEntry defaultValue, String defaultHotkey, String comment, String prettyName) {
        this(name, defaultValue, defaultHotkey, KeybindSettings.DEFAULT, comment, prettyName);
    }

    public ConfigOptionListHotkeyed(String name, IConfigOptionListEntry defaultValue, String defaultHotkey, KeybindSettings settings, String comment, String prettyName) {
        super(name, defaultValue, comment, prettyName);
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackCycleOptionListWithMessage(this));
    }

    @Override
    public IKeybind getKeybind() {
        return this.keybind;
    }

    public void resetToDefault() {
        super.resetToDefault();
        this.keybind.resetToDefault();
    }
}
