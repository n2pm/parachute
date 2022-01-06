package pm.n2.parachute.gui;

import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.config.Configs;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ConfigGui extends GuiConfigsBase {
    private static GuiTabs tab = GuiTabs.TWEAKS;

    public ConfigGui() {
        super(10, 50, Parachute.MOD_ID, null, "parachute.gui.config.title");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;
        int rows = 1;

        for (GuiTabs tab : GuiTabs.values()) {
            int width = this.getStringWidth(tab.getDisplayName()) + 10;

            if (x >= this.width - width - 10) {
                x = 10;
                y += 22;
                rows++;
            }

            x += this.createButton(x, y, width, tab);
        }

        if (rows > 1) {
            int scrollbarPosition = this.getListWidget().getScrollbar().getValue();
            this.setListPosition(this.getListX(), 50 + (rows - 1) * 22);
            this.reCreateListWidget();
            this.getListWidget().getScrollbar().setValue(scrollbarPosition);
            this.getListWidget().refreshEntries();
        }
    }

    private int createButton(int x, int y, int width, GuiTabs tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(ConfigGui.tab != tab);
        this.addButton(button, new ButtonListener(tab, this));

        return button.getWidth() + 2;
    }


    @Override
    protected boolean useKeybindSearch() {
        return ConfigGui.tab == GuiTabs.TWEAKS_HOTKEYS || ConfigGui.tab == GuiTabs.RENDER_HOTKEYS || ConfigGui.tab == GuiTabs.DEBUG_RENDERER_HOTKEYS;
    }

    @Override
    protected int getConfigWidth() {
        GuiTabs tab = ConfigGui.tab;

        // There are no keybinds here so this doesn't need to be huge
        if (tab == GuiTabs.FEATURES || tab == GuiTabs.TWEAKS || tab == GuiTabs.RENDER || tab == GuiTabs.DEBUG_RENDERER || tab == GuiTabs.BUG_FIX) {
            return 100;
        }

        return super.getConfigWidth();
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs;
        GuiTabs tab = ConfigGui.tab;

        switch (tab) {
            case GENERIC:
                configs = Configs.GENERAL_CONFIGS.get();
                break;
            case FEATURES:
                configs = Configs.FEATURE_CONFIGS.get();
                break;
            case FEATURES_HOTKEYS:
                configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, Configs.FEATURE_CONFIGS.getHotkeys());
                break;
            case TWEAKS:
                configs = Configs.TWEAK_CONFIGS.get();
                break;
            case TWEAKS_HOTKEYS:
                configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, Configs.TWEAK_CONFIGS.getHotkeys());
                break;
            case RENDER:
                configs = Configs.RENDER_CONFIGS.get();
                break;
            case RENDER_HOTKEYS:
                configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, Configs.RENDER_CONFIGS.getHotkeys());
                break;
            case DEBUG_RENDERER:
                configs = Configs.DEBUG_RENDERER_CONFIGS.get();
                break;
            case DEBUG_RENDERER_HOTKEYS:
                configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, Configs.DEBUG_RENDERER_CONFIGS.getHotkeys());
                break;
            case BUG_FIX:
                configs = Configs.BUG_FIX_CONFIGS.get();
                break;
            default:
                return Collections.emptyList();
        }

        return ConfigOptionWrapper.createFor(configs);
    }

    public enum GuiTabs {
        GENERIC("parachute.gui.config.generic"),
        FEATURES("parachute.gui.config.features"),
        FEATURES_HOTKEYS("parachute.gui.config.featuresHotkeys"),
        TWEAKS("parachute.gui.config.tweaks"),
        TWEAKS_HOTKEYS("parachute.gui.config.tweaksHotkeys"),
        RENDER("parachute.gui.config.render"),
        RENDER_HOTKEYS("parachute.gui.config.renderHotkeys"),
        DEBUG_RENDERER("parachute.gui.config.debugRenderer"),
        DEBUG_RENDERER_HOTKEYS("parachute.gui.config.debugRendererHotkeys"),
        BUG_FIX("parachute.gui.config.bugFix");

        private final String translationKey;

        GuiTabs(String translationKey) {
            this.translationKey = translationKey;
        }

        public String getDisplayName() {
            return StringUtils.translate(this.translationKey);
        }

    }

    private record ButtonListener(GuiTabs tab, ConfigGui parent) implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {

            ConfigGui.tab = this.tab;

            this.parent.reCreateListWidget(); // apply the new config width
            Objects.requireNonNull(this.parent.getListWidget()).resetScrollbarPosition();
            this.parent.initGui();
        }
    }
}
