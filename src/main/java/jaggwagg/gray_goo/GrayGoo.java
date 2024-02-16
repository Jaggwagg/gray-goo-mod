package jaggwagg.gray_goo;

import jaggwagg.gray_goo.config.ModConfig;
import jaggwagg.gray_goo.config.ModConfigManager;
import jaggwagg.gray_goo.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GrayGoo implements ModInitializer {
    public static final String MOD_ID = "gray_goo";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + File.separator + "/config/" + MOD_ID + ".json";
    public static final ModConfig CONFIG = ModConfigManager.loadConfig(CONFIG_FILE_PATH);

    public static void loggerInfo(String message) {
        LOGGER.info(MOD_ID + ": " + message);
    }

    @Override
    public void onInitialize() {
        RegisterBlocks.init();
        RegisterBlockEntityTypes.init();
        RegisterItems.init();
        RegisterItemGroups.init();
        RegisterScreenHandlers.init();

        LOGGER.info(MOD_ID + ": Initialized common successfully");
    }
}
