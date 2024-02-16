package jaggwagg.gray_goo.registry;

import jaggwagg.gray_goo.GrayGoo;
import jaggwagg.gray_goo.screen.ModScreenHandlers;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class RegisterScreenHandlers {
    public static void init() {
        Arrays.stream(ModScreenHandlers.values()).forEach(value -> registerScreenHandler(value.getId(), value.getScreenHandlerType()));
    }

    private static void registerScreenHandler(String id, ScreenHandlerType<? extends ScreenHandler> screenHandlerType) {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(GrayGoo.MOD_ID, id), screenHandlerType);
    }
}
