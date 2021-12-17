package pm.n2.parachute.gui;

import com.google.common.collect.ImmutableList;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.config.GenericConfigs;
import pm.n2.parachute.config.RenderConfigs;
import pm.n2.parachute.config.TweakConfigs;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ConfigGui extends GuiConfigsBase {
    private static GuiTabs tab = GuiTabs.TWEAKS;
    private static GuiTabs lastTab;

    public ConfigGui() {
        super(10, 50, Parachute.MOD_ID, null, "parachute.gui.config.title");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (GuiTabs tab : GuiTabs.values()) {
            x += this.createButton(x, y, -1, tab);
        }
    }

    private int createButton(int x, int y, int width, GuiTabs tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(ConfigGui.tab != tab);
        this.addButton(button, new ButtonListener(tab, this));

        return button.getWidth() + 2;
    }


    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs;
        GuiTabs tab = ConfigGui.tab;

        switch (tab) {
            case GENERIC:
                configs = GenericConfigs.OPTIONS;
                break;
            case TWEAKS:
                configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, ImmutableList.copyOf(TweakConfigs.OPTIONS));
                break;
            case TWEAKS_HOTKEYS:
                configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, ImmutableList.copyOf(TweakConfigs.OPTIONS));
                break;
            case RENDER:
                configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, ImmutableList.copyOf(RenderConfigs.OPTIONS));
                break;
            case RENDER_HOTKEYS:
                configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, ImmutableList.copyOf(RenderConfigs.OPTIONS));
                break;
            default:
                return Collections.emptyList();
        }

        return ConfigOptionWrapper.createFor(configs);
    }

    private static class ButtonListener implements IButtonActionListener {
        private final ConfigGui parent;
        private final GuiTabs tab;

        public ButtonListener(GuiTabs tab, ConfigGui parent) {
            this.tab = tab;
            this.parent = parent;
        }


        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            ConfigGui.lastTab = ConfigGui.tab;
            ConfigGui.tab = this.tab;

            this.parent.reCreateListWidget(); // apply the new config width
            Objects.requireNonNull(this.parent.getListWidget()).resetScrollbarPosition();
            this.parent.initGui();
        }
    }


    public enum GuiTabs {
        GENERIC("parachute.gui.config.generic"),
        TWEAKS("parachute.gui.config.tweaks"),
        TWEAKS_HOTKEYS("parachute.gui.config.tweaksHotkeys"),
        RENDER("parachute.gui.config.render"),
        RENDER_HOTKEYS("parachute.gui.config.renderHotkeys");

        private final String translationKey;

        GuiTabs(String translationKey) {
            this.translationKey = translationKey;
        }

        public String getDisplayName() {
            return StringUtils.translate(this.translationKey);
        }

    }
}
