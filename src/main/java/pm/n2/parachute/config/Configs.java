package pm.n2.parachute.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

import java.util.ArrayList;
import java.util.List;

// adryd: I'm new to Java please don't kill me lmao
public class Configs {
    public static GeneralConfigs GENERAL_CONFIGS = new GeneralConfigs();
    public static FeatureConfigs FEATURE_CONFIGS = new FeatureConfigs();
    public static TweakConfigs TWEAK_CONFIGS = new TweakConfigs();
    public static RenderConfigs RENDER_CONFIGS = new RenderConfigs();

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
        public static final ConfigBoolean WORLDEDIT_CUI = new ConfigBooleanHotkeyed("worldeditCUI", false, "", "Enables WorldEdit CUI", "WorldEdit CUI");
        public static final ConfigBoolean RECONNECT_BUTTON = new ConfigBoolean("reconnectButton", false, "Adds a button to reconnect when you disconnect from a server.", "Show reconnect button");
        public static final ConfigBoolean AUTO_RECONNECT_ENABLED = new ConfigBoolean("tweakAutoReconnect", false, "Automatically reconnect after you disconnect from a server.", "Auto reconnect");
        public static final ConfigInteger AUTO_RECONNECT_TIME = new ConfigInteger("autoReconnectTimeout", 5, 0, 30, "The amount of seconds until auto reconnect triggers.");


        public FeatureConfigs() {
            super(ImmutableList.of(
                    WORLDEDIT_CUI,
                    RECONNECT_BUTTON,
                    AUTO_RECONNECT_ENABLED,
                    AUTO_RECONNECT_TIME
            ));
        }
    }

    public static class TweakConfigs extends BaseConfigs {
        public static final ConfigOptionListHotkeyed SHOW_NAMETAGS = new ConfigOptionListHotkeyed("teamNametagRule", FeatureOverride.DEFAULT, "", "Overrides team settings for nametags.", "Show player nametags");
        public static final ConfigBooleanHotkeyed FORCE_COPY_DEBUG_INFO = new ConfigBooleanHotkeyed("forceCopyDebugInfo", false, "", "Copy all known info when using F3 + I, even when not op.", "Force copy debug info");
        public static final ConfigBooleanHotkeyed FORCE_DETAILED_DEBUG_INFO = new ConfigBooleanHotkeyed("noReducedDebugInfo", false, "", "Overrides reduced debug info.", "Force detailed debug info");
        public static final ConfigBoolean NO_SERVER_RESOURCE_PACKS = new ConfigBooleanHotkeyed("noServerResourcePacks", false, "", "Tells the server you accepted a resource pack without applying it.", "Ignore server resource packs");
        public static final ConfigBooleanHotkeyed THIRD_PERSON_CAMERA_NO_CLIP = new ConfigBooleanHotkeyed("3rdPersonCameraClip", false, "", "Allow 3rd person camera to clip", "Third person camera noclip");
        public static final ConfigBooleanHotkeyed CUSTOM_PLAYER_LIST_LENGTH_ENABLED = new ConfigBooleanHotkeyed("customPlayerListLengthEnabled", false, "", "Overwrite the player list length", "Overwrite player list length");
        public static final ConfigInteger CUSTOM_PLAYER_LIST_LENGTH = new ConfigInteger("customPlayerListLength", 80, 0, 1000, "Overwrites the tab list length; requires \"Overwrite player list length\"");
        public static final ConfigBooleanHotkeyed NO_BOOK_PAGE_LENGTH = new ConfigBooleanHotkeyed("noBookPageLengthLimit", false, "", "Ignore book page length when creating books", "Ignore book page length when creating books");
        public static final ConfigBoolean SKIN_SIDELOADING_ENABLED = new ConfigBoolean("skinSideloading", false, "Disable skin domain checks in authlib and skin size checks", "Skin sideloading");
        public static final ConfigBoolean SKIN_SIDELOADING_NON_MOJANG_DOMAINS = new ConfigBoolean("skinSideloadingNonMojangDomains", false, "Allow loading of skins from non Mojang domains. This could reveal your IP to 3rd parties (like anybody actually cares)", "Sideload skins from non Mojang domains");
        public static final ConfigBoolean NO_SERVER_BLOCKIST = new ConfigBoolean("noMojangServerBlocklist", false, "Bypass Mojang's multiplayer server blocklist for EULA violating servers. Don't actually do this lol", "Disable Mojang's multiplayer server blocklist");
        public static final ConfigBooleanHotkeyed STEP_ASSIST_ENABLED = new ConfigBooleanHotkeyed("stepAssistEnabled", false, "", "Sets block step height to 1 block", "Step assist");
        public static final ConfigDouble STEP_ASSIST_HEIGHT = new ConfigDouble("stepAssistHeight", 1.0, 0.0, 2.0,true, "Step assist height");
        public static final ConfigBooleanHotkeyed BRIGADIER_STRING_ESCAPES = new ConfigBooleanHotkeyed("brigadierStringEscapes", false, "", "A backport of brigadier#90. Makes stringified NBTs support more JSON-like string escapes. by Mstrodl\nRequires a compatible server if using on multiplayer", "Brigardier better string escapes");
        public static final ConfigBooleanHotkeyed CUSTOM_CHAT_HISTORY_LENGTH_ENABLED = new ConfigBooleanHotkeyed("customChatHistoryLengthEnabled", false, "", "Allow for chat length to be overwritten", "Overwrite chat length");
        public static final ConfigInteger CUSTOM_CHAT_HISTORY_LENGTH = new ConfigInteger("customChatHistoryLength", 100, 1, 10000,false, "Chat length");
        public static final ConfigBooleanHotkeyed ALLOW_DISALLOWED_CHARS = new ConfigBooleanHotkeyed("allowDisallowedChars", false, "", "Allow use of disallowed chars for command blocks or a way of kicking yourself from a multiplayer server", "Allow use of disallowed chars");

        public TweakConfigs() {
            super(ImmutableList.of(
                    SHOW_NAMETAGS,
                    FORCE_COPY_DEBUG_INFO,
                    FORCE_DETAILED_DEBUG_INFO,
                    NO_SERVER_RESOURCE_PACKS,
                    THIRD_PERSON_CAMERA_NO_CLIP,
                    CUSTOM_PLAYER_LIST_LENGTH_ENABLED,
                    CUSTOM_PLAYER_LIST_LENGTH,
                    NO_BOOK_PAGE_LENGTH,
                    SKIN_SIDELOADING_ENABLED,
                    SKIN_SIDELOADING_NON_MOJANG_DOMAINS,
                    NO_SERVER_BLOCKIST,
                    STEP_ASSIST_ENABLED,
                    STEP_ASSIST_HEIGHT,
                    BRIGADIER_STRING_ESCAPES,
                    CUSTOM_CHAT_HISTORY_LENGTH_ENABLED,
                    CUSTOM_CHAT_HISTORY_LENGTH,
                    ALLOW_DISALLOWED_CHARS
            ));
        }
    }

    public static class RenderConfigs extends BaseConfigs {
        public static final ConfigBooleanHotkeyed HIDE_SCOREBOARD_NUMBERS = new ConfigBooleanHotkeyed("noScoreboardNumbers", false, "", "Disable scoreboard numbers", "No scoreboard numbers");
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
        public static final ConfigBooleanHotkeyed NO_NIGHT_VISION_FLASHING = new ConfigBooleanHotkeyed("noNightVisionFlashing", false, "", "Disable flashing when night vision potion is ending", "Disable night vision flashing");
        public static final ConfigBooleanHotkeyed NO_BLINDNESS = new ConfigBooleanHotkeyed("noBlindnessFog", false, "", "Disables blindness fog");
        public static final ConfigBooleanHotkeyed NO_TORCH_FLICKER = new ConfigBooleanHotkeyed("noTorchFlicker", false, "", "Disables the subtle flicker from artificial light sources");
        public static final ConfigBooleanHotkeyed SCALE_DEBUG_PIE = new ConfigBooleanHotkeyed("scaleDebugPie", false, "", "Scale's the debug pie to the gui scale of the game", "Scale debug pie");

        RenderConfigs() {
            super(ImmutableList.of(
                    HIDE_SCOREBOARD_NUMBERS,
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
                    NO_NIGHT_VISION_FLASHING,
                    NO_BLINDNESS,
                    NO_TORCH_FLICKER,
                    SCALE_DEBUG_PIE
            ));
        }
    }


}
