package pm.n2.parachute.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;

public class TweakConfigs {
    public static final ConfigBooleanHotkeyed TWEAK_WORLDEDIT_CUI = new ConfigBooleanHotkeyed("tweakWorldeditCUI", false, "", "Enables Worldedit CUI", "Worldedit CUI");
    public static final ConfigBooleanHotkeyed TWEAK_FORCE_SHOW_NAMETAG = new ConfigBooleanHotkeyed("tweakNoTeamNametagRule", false, "", "Overrides team settings to always show nametags.", "Force show player nametags");
    public static final ConfigBooleanHotkeyed TWEAK_RECONNECT_BUTTON = new ConfigBooleanHotkeyed("tweakReconnectButton", false, "", "Adds a button to reconnect when you disconnect from a server.", "Show reconnect button");
    public static final ConfigBooleanHotkeyed TWEAK_AUTO_RECONNECT_ENABLED = new ConfigBooleanHotkeyed("tweakAutoReconnect", false, "", "Automatically reconnect after you disconnect from a server.", "Auto reconnect");
    public static final ConfigBooleanHotkeyed TWEAK_FORCE_COPY_DEBUG_INFO = new ConfigBooleanHotkeyed("tweakForceCopyDebugInfo", false, "", "Copy all known info when using F3 + I, even when not op.", "Force copy debug info");
    public static final ConfigBooleanHotkeyed TWEAK_FORCE_DETAILED_DEBUG_INFO = new ConfigBooleanHotkeyed("tweakNoReducedDebugInfo", false, "", "Overrides reduced debug info.", "Force detailed debug info");
    public static final ConfigBooleanHotkeyed TWEAK_IGNORE_RESOURCE_PACKS = new ConfigBooleanHotkeyed("tweakNoServerResourcePacks", false, "", "Tells the server you accepted a resource pack without applying it.", "Ignore server resource packs");
    public static final ConfigBooleanHotkeyed TWEAK_THIRD_PERSON_CAMERA_NO_CLIP = new ConfigBooleanHotkeyed("tweak3rdPersonCameraClip", false, "", "Allow 3rd person camera to clip", "Third person camera noclip");
    public static final ConfigBooleanHotkeyed TWEAK_PLAYER_LIST_LENGTH = new ConfigBooleanHotkeyed("tweakPlayerListLength", false, "", "Overwrite the player list length", "Overwrite player list length");
    public static final ConfigBooleanHotkeyed TWEAK_IGNORE_BOOK_PAGE_LENGTH = new ConfigBooleanHotkeyed("tweakNoBookPageLengthLimit", false, "", "Ignore book page length when creating books", "Ignore book page length when creating books");
    public static final ConfigBooleanHotkeyed TWEAK_SKIN_SIDELOADING = new ConfigBooleanHotkeyed("tweakSkinSideloading", false, "", "Disable skin domain checks in authlib and skin size checks", "Skin sideloading");
    public static final ConfigBooleanHotkeyed TWEAK_BYPASS_SERVER_BLOCKIST = new ConfigBooleanHotkeyed("tweakNoMojangServerBlocklist", false, "", "Probably don't do this", "Bypass Mojang's server blocklist");
    public static final ConfigBooleanHotkeyed TWEAK_STEP_ASSIST = new ConfigBooleanHotkeyed("tweakStepAssist", false, "", "Sets block step height to 1 block", "Step assist");
    public static final ConfigBooleanHotkeyed TWEAK_BRIGADIER_STRING_ESCAPES = new ConfigBooleanHotkeyed("tweakBrigadierStringEscapes", false, "", "A backport of brigadier#90. Makes stringified NBTs support more JSON-like string escapes. by Mstrodl\nRequires a compatible server if using on multiplayer", "Brigardier better string escapes");

    public static final ImmutableList<IHotkeyTogglable> OPTIONS = ImmutableList.of(
            TWEAK_WORLDEDIT_CUI,
            TWEAK_FORCE_SHOW_NAMETAG,
            TWEAK_RECONNECT_BUTTON,
            TWEAK_AUTO_RECONNECT_ENABLED,
            TWEAK_FORCE_COPY_DEBUG_INFO,
            TWEAK_FORCE_DETAILED_DEBUG_INFO,
            TWEAK_IGNORE_RESOURCE_PACKS,
            TWEAK_THIRD_PERSON_CAMERA_NO_CLIP,
            TWEAK_PLAYER_LIST_LENGTH,
            TWEAK_IGNORE_BOOK_PAGE_LENGTH,
            TWEAK_SKIN_SIDELOADING,
            TWEAK_BYPASS_SERVER_BLOCKIST,
            TWEAK_STEP_ASSIST,
            TWEAK_BRIGADIER_STRING_ESCAPES
    );
}
