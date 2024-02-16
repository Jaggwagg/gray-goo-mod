package jaggwagg.gray_goo.client.register;

import jaggwagg.gray_goo.client.gui.screen.ModHandledScreens;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;

import java.util.Arrays;

public class RegisterHandledScreens {
    public static void init() {
        Arrays.stream(ModHandledScreens.values()).forEach(value -> registerHandledScreen(value.getScreenHandlerType(), value.getProvider()));
    }

    /*
     * Suppressing multiple warnings here.
     * Must be done in order to allow multiple entity types within enums.
     * Works every time, even on Forgified Fabric API with Sinytra Connector.
     * Why must Java not have generic enums :(
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void registerHandledScreen(ScreenHandlerType screenHandlerType, HandledScreens.Provider provider) {
        HandledScreens.register(screenHandlerType, provider);
    }
}
