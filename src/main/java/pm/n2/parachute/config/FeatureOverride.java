package pm.n2.parachute.config;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;

public enum FeatureOverride implements IConfigOptionListEntry {
    DEFAULT("default", "default"),
    FORCE_TRUE("true", "§2true"),
    FORCE_FALSE("false", "§4false");

    private final String configString;
    private final String unlocName;

    FeatureOverride(String configString, String unlocName) {
        this.configString = configString;
        this.unlocName = unlocName;
    }

    public String getStringValue() {
        return this.configString;
    }

    public String getDisplayName() {
        return StringUtils.translate(this.unlocName);
    }

    public IConfigOptionListEntry cycle(boolean forward) {
        int id = this.ordinal();
        if (forward) {
            ++id;
            if (id >= values().length) {
                id = 0;
            }
        } else {
            --id;
            if (id < 0) {
                id = values().length - 1;
            }
        }

        return values()[id % values().length];
    }

    public FeatureOverride fromString(String name) {
        return fromStringStatic(name);
    }

    public static FeatureOverride fromStringStatic(String name) {
        FeatureOverride[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            FeatureOverride featureOverride = var1[var3];
            if (featureOverride.configString.equalsIgnoreCase(name)) {
                return featureOverride;
            }
        }

        return DEFAULT;
    }
}
