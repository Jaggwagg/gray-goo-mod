package jaggwagg.gray_goo;

import jaggwagg.gray_goo.block.GrayGooBlocks;
import jaggwagg.gray_goo.config.GrayGooConfig;
import jaggwagg.gray_goo.item.GrayGooItems;
import jaggwagg.gray_goo.screen.GrayGooScreenHandlers;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GrayGoo implements ModInitializer {
    public static final String MOD_ID = "gray_goo";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final GrayGooConfig CONFIG = GrayGooConfig.getConfig();

    @Override
    public void onInitialize() {
        GrayGooBlocks.init();
        GrayGooItems.init();
        GrayGooScreenHandlers.init();
        LOGGER.info("Successfully initialized main!");
    }
}
