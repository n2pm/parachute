package pm.n2.parachute;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pm.n2.parachute.config.GenericConfigs;
import pm.n2.parachute.config.RenderConfigs;
import pm.n2.parachute.config.TweakConfigs;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

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

                ConfigUtils.readHotkeyToggleOptions(root, "tweakKeys", "tweaks", TweakConfigs.OPTIONS);
                ConfigUtils.readHotkeyToggleOptions(root, "renderKeys", "render", RenderConfigs.OPTIONS);
                ConfigUtils.readConfigBase(root, "generic", GenericConfigs.OPTIONS);
            }
        }
    }

    public static void saveToFile() {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();

            ConfigUtils.writeHotkeyToggleOptions(root, "tweakKeys", "tweaks", TweakConfigs.OPTIONS);
            ConfigUtils.writeHotkeyToggleOptions(root, "renderKeys", "render", RenderConfigs.OPTIONS);
            ConfigUtils.writeConfigBase(root, "generic", GenericConfigs.OPTIONS);

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
