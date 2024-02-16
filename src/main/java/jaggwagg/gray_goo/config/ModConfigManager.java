package jaggwagg.gray_goo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import jaggwagg.gray_goo.GrayGoo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModConfigManager {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveConfig(ModConfig config, String filePath) {
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                gson.toJson(config, writer);
                GrayGoo.loggerInfo("Configuration saved successfully");
            }
        } catch (IOException e) {
            GrayGoo.loggerInfo("Unable to write file: " + e.getMessage());
        }
    }

    public static ModConfig loadConfig(String filePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ModConfig.class, new ModConfigDeserializer())
                .create();
        ModConfig loadedConfig = new ModConfig();
        boolean shouldCreateNewConfig = false;

        try (FileReader reader = new FileReader(filePath)) {
            try {
                loadedConfig = gson.fromJson(reader, ModConfig.class);
            } catch (JsonParseException e) {
                GrayGoo.loggerInfo("Invalid config file: \n" + e.getMessage());
                shouldCreateNewConfig = true;
            }
        } catch (IOException e) {
            GrayGoo.loggerInfo("Could not read config file: \n" + e.getMessage());
            shouldCreateNewConfig = true;
        }

        if (shouldCreateNewConfig) {
            ModConfigManager.backupConfig(filePath);
            ModConfigManager.saveConfig(loadedConfig, filePath);
            GrayGoo.loggerInfo("Config file not found or corrupted, creating a new default config file and backed up current one");
        } else {
            GrayGoo.loggerInfo("Config loaded successfully");
        }

        return loadedConfig;
    }

    public static void backupConfig(String filePath) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String backupFilePath = filePath + "." + timestamp + ".backup";

        try {
            Files.copy(Path.of(filePath), Path.of(backupFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            GrayGoo.loggerInfo("Original config file does not exist, making a new one without backup");
        }
    }
}
