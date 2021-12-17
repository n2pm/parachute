package pm.n2.parachute.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;

public class RenderConfigs {
    public static final ConfigBooleanHotkeyed RENDER_HIDE_SCOREBOARD_NUMBERS = new ConfigBooleanHotkeyed("renderNoScoreboardNumbers", false, "", "Disable scoreboard numbers", "No scoreboard numbers");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_SCOREBOARD = new ConfigBooleanHotkeyed("renderNoScoreboard", false, "", "Disable scoreboard", "No scoreboard");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_BOSSBAR = new ConfigBooleanHotkeyed("renderNoBossBar", false, "", "Disable boss bar", "No boss bar");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_FIRE_OVERLAY = new ConfigBooleanHotkeyed("renderNoFireOverlay", false, "", "Disable fire overlay", "No fire overlay");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_UNDERWATER_OVERLAY = new ConfigBooleanHotkeyed("renderNoUnderwaterOverlay", false, "", "Disable underwater overlay", "No underwater overlay");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_INBLOCK_OVERLAY = new ConfigBooleanHotkeyed("renderNoBlockOverlay", false, "", "Disable in block overlay", "No in block overlay");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_CROSSHAIR = new ConfigBooleanHotkeyed("renderNoCrosshair", false, "", "Disable crosshair", "No crosshair");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_SPYGLASS_OVERLAY = new ConfigBooleanHotkeyed("renderNoSpyglassOverlay", false, "", "Disable spyglass overlay", "No spyglass overlay");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_PUMPKIN_OVERLAY = new ConfigBooleanHotkeyed("renderNoPumpkinOverlay", false, "", "Disable pumpkin overlay", "No pumpkin overlay");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_POWDER_SNOW_OVERLAY = new ConfigBooleanHotkeyed("renderNoPowderSnowOverlay", false, "", "Disable powder snow overlay", "No powder snow overlay");
    public static final ConfigBooleanHotkeyed RENDER_HIDE_VIGNETTE = new ConfigBooleanHotkeyed("renderNoVignette", false, "", "Disable vignette", "No powder vignette");
    public static final ConfigBooleanHotkeyed RENDER_FORCE_BLOCK_OUTLINE = new ConfigBooleanHotkeyed("renderForceBlockOutline", false, "", "Forces the block outline to show in spectator and adventure mode", "Force show block outline");
    public static final ConfigBooleanHotkeyed RENDER_FORCE_GLOWING = new ConfigBooleanHotkeyed("renderForceEntityGlowing", false, "", "Forces all entities to glow", "Force entity glowing");
    public static final ConfigBooleanHotkeyed RENDER_NIGHT_VISION = new ConfigBooleanHotkeyed("renderNightVision", false, "", "Enables night vision\nMeant to be an alternative to tweakeroo's tweakGammaOverride");
    public static final ConfigBooleanHotkeyed RENDER_NO_NIGHT_VISION_FLASHING = new ConfigBooleanHotkeyed("renderNoNightVisionFlashing", false, "", "Disable flashing when night vision potion is ending", "Disable night vision flashing");
    public static final ConfigBooleanHotkeyed RENDER_NO_BLINDNESS = new ConfigBooleanHotkeyed("renderNoBlindnessFog", false, "", "Disables blindness fog");
    public static final ConfigBooleanHotkeyed RENDER_NO_TORCH_FLICKER = new ConfigBooleanHotkeyed("renderNoTorchFlicker", false, "", "Disables the subtle flicker from artificial light sources");

    public static final ImmutableList<IHotkeyTogglable> OPTIONS = ImmutableList.of(
            RENDER_HIDE_SCOREBOARD_NUMBERS,
            RENDER_HIDE_SCOREBOARD,
            RENDER_HIDE_BOSSBAR,
            RENDER_HIDE_FIRE_OVERLAY,
            RENDER_HIDE_UNDERWATER_OVERLAY,
            RENDER_HIDE_INBLOCK_OVERLAY,
            RENDER_HIDE_CROSSHAIR,
            RENDER_HIDE_SPYGLASS_OVERLAY,
            RENDER_HIDE_PUMPKIN_OVERLAY,
            RENDER_HIDE_POWDER_SNOW_OVERLAY,
            RENDER_HIDE_VIGNETTE,
            RENDER_FORCE_BLOCK_OUTLINE,
            RENDER_FORCE_GLOWING,
            RENDER_NIGHT_VISION,
            RENDER_NO_NIGHT_VISION_FLASHING,
            RENDER_NO_BLINDNESS,
            RENDER_NO_TORCH_FLICKER
    );
}
