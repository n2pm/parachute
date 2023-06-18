package pm.n2.parachute.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

// adryd: I'm new to Java please don't kill me lmao
public class Configs {
    public static GeneralConfigs GENERAL_CONFIGS = new GeneralConfigs();
    public static FeatureConfigs FEATURE_CONFIGS = new FeatureConfigs();
    public static TweakConfigs TWEAK_CONFIGS = new TweakConfigs();
    public static RenderConfigs RENDER_CONFIGS = new RenderConfigs();
    public static DebugRendererConfigs DEBUG_RENDERER_CONFIGS = new DebugRendererConfigs();
    public static BugFixConfigs BUG_FIX_CONFIGS = new BugFixConfigs();

    public static class BaseConfigs {
        public final ImmutableList<IConfigValue> OPTIONS;

        public BaseConfigs(ImmutableList<IConfigValue> options) {
            this.OPTIONS = options;
        }

        public ImmutableList<IConfigValue> get() {
            return OPTIONS;
        }

        public ImmutableList<IHotkey> getHotkeys() {
            List<IHotkey> list = new ArrayList<>();
            for (IConfigValue configValue : this.OPTIONS) {
                if (configValue instanceof IHotkey) {
                    list.add(((IHotkey) configValue));
                }
            }
            return ImmutableList.copyOf(list);
        }

        public ImmutableList<IKeybind> getKeybinds() {
            List<IKeybind> list = new ArrayList<>();
            for (IConfigValue configValue : this.OPTIONS) {
                if (configValue instanceof IHotkey) {
                    list.add(((IHotkey) configValue).getKeybind());
                }
            }
            return ImmutableList.copyOf(list);
        }
    }

    public static class GeneralConfigs extends BaseConfigs {
        public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGUI", "O,C", "Keybind to open the Parachute config.");
        public static final ConfigHotkey OPEN_CLIENT_COMMANDS = new ConfigHotkey("openClientCommands", "PERIOD", KeybindSettings.RELEASE, "Keybind to client commands menu.");

        public GeneralConfigs() {
            super(ImmutableList.of(
                    OPEN_CONFIG_GUI,
                    OPEN_CLIENT_COMMANDS
            ));
        }
    }

    public static class FeatureConfigs extends BaseConfigs {
        public static final ConfigBoolean RECONNECT_BUTTON = new ConfigBoolean("reconnectButton", false, "Adds a button to reconnect when you disconnect from a server.", "Show reconnect button");
        public static final ConfigBoolean AUTO_RECONNECT_ENABLED = new ConfigBoolean("tweakAutoReconnect", false, "Automatically reconnect after you disconnect from a server.", "Auto reconnect");
        public static final ConfigInteger AUTO_RECONNECT_TIME = new ConfigInteger("autoReconnectTimeout", 5, 0, 30, "The amount of seconds until auto reconnect triggers.");
        public static final ConfigBooleanHotkeyed POTION_EFFECT_HUD = new ConfigBooleanHotkeyed("potionEffectHUD", false, "", "Enables minimal potion effect HUD", "Potion effect HUD");
        public static final ConfigBoolean POTION_EFFECT_HUD_NO_COLOR = new ConfigBoolean("potionEffectHUDNoColor", false, "Disables potion color on effect HUD", "Potion effect HUD disable color");
        public static final ConfigBooleanHotkeyed ARMOR_HUD = new ConfigBooleanHotkeyed("armorHUD", false, "", "Enables armor HUD. \nPorted from blanket", "Armor HUD");
        public static final ConfigBoolean PORTAL_SCREENS = new ConfigBoolean("portalScreens", false, "Allow opening screens whilst in portals", "Portal Screens");
        public static final ConfigHotkey SWAP_HOTBAR = new ConfigHotkey("swapHotbar", "", KeybindSettings.RELEASE, "Keybind to swap the hotbar and bottom inventory row", "Swap hotbar");

        public FeatureConfigs() {
            super(ImmutableList.of(
                    RECONNECT_BUTTON,
                    AUTO_RECONNECT_ENABLED,
                    AUTO_RECONNECT_TIME,
                    POTION_EFFECT_HUD,
                    POTION_EFFECT_HUD_NO_COLOR,
                    ARMOR_HUD,
                    PORTAL_SCREENS,
                    SWAP_HOTBAR
            ));
        }
    }

    public static class TweakConfigs extends BaseConfigs {
        public static final ConfigOptionListHotkeyed SHOW_NAMETAGS = new ConfigOptionListHotkeyed("teamNametagRule", FeatureOverride.DEFAULT, "", "Overrides team settings for name tags.", "Show player name tags");
        public static final ConfigBooleanHotkeyed FORCE_COPY_DEBUG_INFO = new ConfigBooleanHotkeyed("forceCopyDebugInfo", false, "", "Copy all known info when using F3 + I, even when not op.", "Force copy debug info");
        public static final ConfigBooleanHotkeyed FORCE_DETAILED_DEBUG_INFO = new ConfigBooleanHotkeyed("noReducedDebugInfo", false, "", "Overrides reduced debug info.", "Force detailed debug info");
        public static final ConfigBoolean NO_SERVER_RESOURCE_PACKS = new ConfigBooleanHotkeyed("noServerResourcePacks", false, "", "Tells the server you accepted a resource pack without applying it.", "Ignore server resource packs");
        public static final ConfigBooleanHotkeyed THIRD_PERSON_CAMERA_NO_CLIP = new ConfigBooleanHotkeyed("3rdPersonCameraClip", false, "", "Allow 3rd person camera to clip", "Third person camera noclip");
        public static final ConfigBooleanHotkeyed CUSTOM_PLAYER_LIST_LENGTH_ENABLED = new ConfigBooleanHotkeyed("customPlayerListLengthEnabled", false, "", "Overwrite the player list length", "Overwrite player list length");
        public static final ConfigInteger CUSTOM_PLAYER_LIST_LENGTH = new ConfigInteger("customPlayerListLength", 80, 0, 1000, "Overwrites the tab list length; requires \"Overwrite player list length\"");
        public static final ConfigBooleanHotkeyed PLAYER_LIST_PING = new ConfigBooleanHotkeyed("playerListPing", false, "", "Shows player list ping in milliseconds.\nPorted from blanket.", "Player list ping (unfinished)");
        public static final ConfigBooleanHotkeyed NO_BOOK_PAGE_LENGTH = new ConfigBooleanHotkeyed("noBookPageLengthLimit", false, "", "Ignore book page length when creating books", "Ignore book page length when creating books");
        //public static final ConfigBoolean SKIN_SIDELOADING_ENABLED = new ConfigBoolean("skinSideloading", false, "Disable skin domain checks in authlib and skin size checks", "Skin sideloading");
        //public static final ConfigBoolean SKIN_SIDELOADING_NON_MOJANG_DOMAINS = new ConfigBoolean("skinSideloadingNonMojangDomains", false, "Allow loading of skins from non Mojang domains. This could reveal your IP to 3rd parties (like anybody actually cares)", "Side load skins from non Mojang domains");
        public static final ConfigBoolean NO_SERVER_BLOCKIST = new ConfigBoolean("noMojangServerBlocklist", false, "Bypass Mojang's multiplayer server blocklist for EULA violating servers. Don't actually do this lol", "Disable Mojang's multiplayer server blocklist");
        public static final ConfigBooleanHotkeyed STEP_ASSIST_ENABLED = new ConfigBooleanHotkeyed("stepAssistEnabled", false, "", "Sets block step height to 1 block", "Step assist");
        public static final ConfigDouble STEP_ASSIST_HEIGHT = new ConfigDouble("stepAssistHeight", 1.0, 0.0, 2.0, true, "Step assist height");
        //        public static final ConfigBooleanHotkeyed HALF_STEP = new ConfigBooleanHotkeyed("halfStep", false, "", "Step up when in air", "Half step");
        public static final ConfigBoolean CUSTOM_CHAT_HISTORY_LENGTH_ENABLED = new ConfigBoolean("customChatHistoryLengthEnabled", false, "Allow for chat length to be overwritten", "Overwrite chat length");
        public static final ConfigInteger CUSTOM_CHAT_HISTORY_LENGTH = new ConfigInteger("customChatHistoryLength", 100, 1, 10000, false, "Chat length");
        public static final ConfigBoolean DONT_RESET_CHAT_HISTORY = new ConfigBoolean("dontResetChatHistory", false, "Don't reset chat history when logging out", "Don't reset chat history");
        public static final ConfigBooleanHotkeyed ALLOW_DISALLOWED_CHARS = new ConfigBooleanHotkeyed("allowDisallowedChars", false, "", "Allow use of disallowed chars for command blocks or a way of kicking yourself from a multiplayer server", "Allow use of disallowed chars");
        public static final ConfigOptionListHotkeyed IS_CHRISTMAS = new ConfigOptionListHotkeyed("isItChristmas", FeatureOverride.DEFAULT, "", "Overrides christmas state for chests.", "Is it Christmas?");
        public static final ConfigBoolean TITLE_BAR_CUSTOMIZATION_ENABLED = new ConfigBoolean("titleBarCustomizationEnabled", false, "Enables customization of the title bar", "Title bar customization");
        public static final ConfigBoolean TITLE_BAR_HIDE_MODDED = new ConfigBoolean("titleBarHideModded", false, "Hides asterisk in title bar", "Title bar hide modded status");
        public static final ConfigBoolean TITLE_BAR_HIDE_GAME_STATUS = new ConfigBoolean("titleBarHideGameStatus", false, "Hides game status from title bar. eg. 3rd party multiplayer", "Title bar hide game status");
        public static final ConfigBoolean MULTIPLAYER_SCREEN_DETAILED_VERSION_INFO = new ConfigBoolean("multiplayerDetailedVersionInfo", false, "Additional version info such as protocol and server brand shown on server selection screen", "Show version info on server selection screen");

        public TweakConfigs() {
            super(ImmutableList.of(
                    SHOW_NAMETAGS,
                    FORCE_COPY_DEBUG_INFO,
                    FORCE_DETAILED_DEBUG_INFO,
                    NO_SERVER_RESOURCE_PACKS,
                    THIRD_PERSON_CAMERA_NO_CLIP,
                    CUSTOM_PLAYER_LIST_LENGTH_ENABLED,
                    CUSTOM_PLAYER_LIST_LENGTH,
                    PLAYER_LIST_PING,
                    NO_BOOK_PAGE_LENGTH,
//                    SKIN_SIDELOADING_ENABLED,
                    //SKIN_SIDELOADING_NON_MOJANG_DOMAINS,
                    NO_SERVER_BLOCKIST,
                    STEP_ASSIST_ENABLED,
                    STEP_ASSIST_HEIGHT,
//                    HALF_STEP,
                    CUSTOM_CHAT_HISTORY_LENGTH_ENABLED,
                    CUSTOM_CHAT_HISTORY_LENGTH,
                    DONT_RESET_CHAT_HISTORY,
                    ALLOW_DISALLOWED_CHARS,
                    IS_CHRISTMAS,
                    TITLE_BAR_CUSTOMIZATION_ENABLED,
                    TITLE_BAR_HIDE_MODDED,
                    TITLE_BAR_HIDE_GAME_STATUS,
                    MULTIPLAYER_SCREEN_DETAILED_VERSION_INFO
            ));
        }
    }

    public static class RenderConfigs extends BaseConfigs {
        public static final ConfigBooleanHotkeyed HIDE_SCOREBOARD = new ConfigBooleanHotkeyed("noScoreboard", false, "", "Disable scoreboard", "No scoreboard");
        public static final ConfigBooleanHotkeyed HIDE_BOSSBAR = new ConfigBooleanHotkeyed("noBossBar", false, "", "Disable boss bar", "No boss bar");
        public static final ConfigBooleanHotkeyed HIDE_FIRE_OVERLAY = new ConfigBooleanHotkeyed("noFireOverlay", false, "", "Disable fire overlay", "No fire overlay");
        public static final ConfigBooleanHotkeyed HIDE_UNDERWATER_OVERLAY = new ConfigBooleanHotkeyed("noUnderwaterOverlay", false, "", "Disable underwater overlay", "No underwater overlay");
        public static final ConfigBooleanHotkeyed HIDE_INBLOCK_OVERLAY = new ConfigBooleanHotkeyed("noBlockOverlay", false, "", "Disable in block overlay", "No in block overlay");
        public static final ConfigOptionListHotkeyed SHOW_CROSSHAIR = new ConfigOptionListHotkeyed("showCrosshair", FeatureOverride.DEFAULT, "", "Show crosshair", "Show crosshair");
        public static final ConfigBooleanHotkeyed HIDE_SPYGLASS_OVERLAY = new ConfigBooleanHotkeyed("noSpyglassOverlay", false, "", "Disable spyglass overlay", "No spyglass overlay");
        public static final ConfigBooleanHotkeyed HIDE_PUMPKIN_OVERLAY = new ConfigBooleanHotkeyed("noPumpkinOverlay", false, "", "Disable pumpkin overlay", "No pumpkin overlay");
        public static final ConfigBooleanHotkeyed HIDE_POWDER_SNOW_OVERLAY = new ConfigBooleanHotkeyed("noPowderSnowOverlay", false, "", "Disable powder snow overlay", "No powder snow overlay");
        public static final ConfigBooleanHotkeyed HIDE_VIGNETTE = new ConfigBooleanHotkeyed("noVignette", false, "", "Disable vignette", "No powder vignette");
        public static final ConfigOptionListHotkeyed SHOW_BLOCK_OUTLINE = new ConfigOptionListHotkeyed("showBlockOutline", FeatureOverride.DEFAULT, "", "Forces the block outline to show in spectator and adventure mode or hide in creative mode", "Show block outline");
        public static final ConfigBooleanHotkeyed FORCE_GLOWING = new ConfigBooleanHotkeyed("forceEntityGlowing", false, "", "Forces all entities to glow", "Force entity glowing");
        public static final ConfigBooleanHotkeyed NIGHT_VISION = new ConfigBooleanHotkeyed("nightVision", false, "", "Enables night vision\nMeant to be an alternative to tweakeroo's tweakGammaOverride");
        public static final ConfigBooleanHotkeyed NO_BLINDNESS = new ConfigBooleanHotkeyed("noBlindnessFog", false, "", "Disables blindness fog");
        public static final ConfigBooleanHotkeyed NO_EFFECT_HUD = new ConfigBooleanHotkeyed("noEffectHud", false, "", "Hides potion effect HUD, ported from blanket", "Hide effect HUD");

        RenderConfigs() {
            super(ImmutableList.of(
                    HIDE_SCOREBOARD,
                    HIDE_BOSSBAR,
                    HIDE_FIRE_OVERLAY,
                    HIDE_UNDERWATER_OVERLAY,
                    HIDE_INBLOCK_OVERLAY,
                    SHOW_CROSSHAIR,
                    HIDE_SPYGLASS_OVERLAY,
                    HIDE_PUMPKIN_OVERLAY,
                    HIDE_POWDER_SNOW_OVERLAY,
                    HIDE_VIGNETTE,
                    SHOW_BLOCK_OUTLINE,
                    FORCE_GLOWING,
                    NIGHT_VISION,
                    NO_BLINDNESS,
                    NO_EFFECT_HUD
            ));
        }
    }

    public static class BugFixConfigs extends BaseConfigs {
        BugFixConfigs() {
            super(ImmutableList.of());
        }
    }

    public static class DebugRendererConfigs extends BaseConfigs {
        public static final ConfigBooleanHotkeyed WATER = new ConfigBooleanHotkeyed("debugWater", false, "", "", "");
        //        public static final ConfigBooleanHotkeyed CHUNK_BORDER = new ConfigBooleanHotkeyed("chunkBorder", false, "", "", "");
        public static final ConfigBooleanHotkeyed HEIGHTMAP = new ConfigBooleanHotkeyed("debugHeightmap", false, "", "Enables vanilla heightmap debugger", "");
        public static final ConfigBooleanHotkeyed COLLISION = new ConfigBooleanHotkeyed("debugCollision", false, "", "Enables vanilla collision boxes debugger", "");
        public static final ConfigBooleanHotkeyed SKY_LIGHT = new ConfigBooleanHotkeyed("debugSkyLight", false, "", "Enables vanilla sky light debugger", "");
        public static final ConfigBooleanHotkeyed BLOCK_OUTLINE = new ConfigBooleanHotkeyed("debugBlockOutline", false, "", "Enables vanilla block outline debugger", "");
        private static final String requires = "\n" + Formatting.RED + "Requires Ceramic or another serverside mod which provides debug info" + Formatting.RESET + "\nto enable debug sending in ceramic, run: /ceramic sendServerDebugInfo true";
        public static final ConfigBooleanHotkeyed PATHFINDING = new ConfigBooleanHotkeyed("debugPathfinding", false, "", "Enables vanilla pathfinding debugger" + requires, "");
        public static final ConfigBooleanHotkeyed NEIGHBOR_UPDATE = new ConfigBooleanHotkeyed("debugNeighborUpdate", false, "", "Enables vanilla neighbor update debugger" + requires, "");
        public static final ConfigBooleanHotkeyed STRUCTURE = new ConfigBooleanHotkeyed("debugStructure", false, "", "Enables vanilla structure debugger" + requires, "");
        public static final ConfigBooleanHotkeyed WORLD_GEN_ATTEMPT = new ConfigBooleanHotkeyed("debugWorldGenAttempt", false, "", "Enables vanilla world gen attempt debugger" + requires, "");
        public static final ConfigBooleanHotkeyed CHUNK_LOADING = new ConfigBooleanHotkeyed("debugChunkLoading", false, "", "Enables vanilla chunk loading debugger" + requires, "");
        public static final ConfigBooleanHotkeyed VILLAGE = new ConfigBooleanHotkeyed("debugBrain", false, "", "Enables vanilla entity brain debugger" + requires, "");
        public static final ConfigBooleanHotkeyed VILLAGE_SECTIONS = new ConfigBooleanHotkeyed("debugVillageSections", false, "", "Enables vanilla village sections debugger" + requires, "");
        public static final ConfigBooleanHotkeyed BEE = new ConfigBooleanHotkeyed("debugBee", false, "", "" + requires, "Enables vanilla bee debugger");
        public static final ConfigBooleanHotkeyed RAID_CENTER = new ConfigBooleanHotkeyed("debugRaidCenter", false, "", "Enables vanilla raid center debugger" + requires, "");
        public static final ConfigBooleanHotkeyed GOAL_SELECTOR = new ConfigBooleanHotkeyed("debugGoalSelector", false, "", "Enables vanilla goal selector debugger" + requires, "");
        //        public static final ConfigBooleanHotkeyed GAME_TEST = new ConfigBooleanHotkeyed("gameTest", false, "", "" + requires, "");
        public static final ConfigBooleanHotkeyed GAME_EVENT = new ConfigBooleanHotkeyed("debugGameEvent", false, "", "Enables vanilla game event debugger" + requires, "");

        DebugRendererConfigs() {
            super(ImmutableList.of(
                    WATER,
                    HEIGHTMAP,
                    COLLISION,
                    BLOCK_OUTLINE,
                    SKY_LIGHT,
                    PATHFINDING,
                    NEIGHBOR_UPDATE,
                    STRUCTURE,
                    WORLD_GEN_ATTEMPT,
                    CHUNK_LOADING,
                    VILLAGE,
                    VILLAGE_SECTIONS,
                    BEE,
                    RAID_CENTER,
                    GOAL_SELECTOR,
//                    GAME_TEST,
                    GAME_EVENT
            ));
        }
    }
}
