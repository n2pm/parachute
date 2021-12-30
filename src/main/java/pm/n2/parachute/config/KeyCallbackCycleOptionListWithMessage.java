package pm.n2.parachute.config;

import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.InfoUtils;

public class KeyCallbackCycleOptionListWithMessage extends KeyCallbackCycleOptionList {
    public KeyCallbackCycleOptionListWithMessage(ConfigOptionListHotkeyed config) {
        super(config);
    }

    public boolean onKeyAction(KeyAction action, IKeybind key) {
        super.onKeyAction(action, key);
        InfoUtils.printActionbarMessage(String.format("Cycled %s to %s", this.config.getPrettyName(), this.config.getOptionListValue().getDisplayName()));
        return true;
    }

}
