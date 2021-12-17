package pm.n2.parachute.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigInteger;

import java.util.List;

public class GenericConfigs {
    public static final ConfigInteger AUTO_RECONNECT_TIME = new ConfigInteger("autoReconnectTimeout", 5, 0, 30, "The amount of seconds until auto reconnect triggers.");
    public static final ConfigInteger PLAYER_LIST_LENGTH = new ConfigInteger("playerListLength", 80, 0, 1000, "Overwrites the tab list length; requires \"Overwrite player list length\"");
    public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGUI", "O,C", "Keybind to open the Parachute config.");
    public static final ConfigBoolean SKIN_SIDELOADING_NON_MOJANG_DOMAINS = new ConfigBoolean("skinSideloadingNonMojangDomains", false, "this could log your ip btw");
    public static final ImmutableList<IConfigValue> OPTIONS = ImmutableList.of(
            AUTO_RECONNECT_TIME,
            PLAYER_LIST_LENGTH,
            OPEN_CONFIG_GUI,
            SKIN_SIDELOADING_NON_MOJANG_DOMAINS
    );

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            OPEN_CONFIG_GUI
    );
}
