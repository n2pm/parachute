package pm.n2.parachute;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import pm.n2.parachute.config.Configs;

import java.io.File;

// stolen from blanket/tweakeroo lol
public class ParachuteConfig implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = Parachute.MOD_ID + ".json";

    public static void loadFromFile() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);
            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "generic", Configs.GENERAL_CONFIGS.get());
                ConfigUtils.readConfigBase(root, "features", Configs.FEATURE_CONFIGS.get());
                ConfigUtils.readConfigBase(root, "tweaks", Configs.TWEAK_CONFIGS.get());
                ConfigUtils.readHotkeys(root, "tweakKeys", Configs.TWEAK_CONFIGS.getHotkeys());
                ConfigUtils.readConfigBase(root, "render", Configs.RENDER_CONFIGS.get());
                ConfigUtils.readHotkeys(root, "renderKeys", Configs.RENDER_CONFIGS.getHotkeys());
            }
        }
    }

    public static void saveToFile() {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "generic", Configs.GENERAL_CONFIGS.get());
            ConfigUtils.writeConfigBase(root, "features", Configs.FEATURE_CONFIGS.get());
            ConfigUtils.writeConfigBase(root, "tweaks", Configs.TWEAK_CONFIGS.get());
            ConfigUtils.writeHotkeys(root, "tweakKeys", Configs.TWEAK_CONFIGS.getHotkeys());
            ConfigUtils.writeConfigBase(root, "render", Configs.RENDER_CONFIGS.get());
            ConfigUtils.writeHotkeys(root, "renderKeys", Configs.RENDER_CONFIGS.getHotkeys());

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveToFile();
    }
}
