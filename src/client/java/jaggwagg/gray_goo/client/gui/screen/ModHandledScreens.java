package jaggwagg.gray_goo.client.gui.screen;

import jaggwagg.gray_goo.screen.ModScreenHandlers;
import jaggwagg.gray_goo.screen.NaniteModifierScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

/*
 * Suppressing multiple warnings here.
 * Must be done in order to allow multiple entity types within enums.
 * Works every time, even on Forgified Fabric API with Sinytra Connector.
 * Why must Java not have generic enums :(
 */
@SuppressWarnings("rawtypes")
public enum ModHandledScreens {
    NANITE_MODIFIER(ModScreenHandlers.NANITE_MODIFIER.getScreenHandlerType(), (handler, inventory, title) -> new NaniteModifierScreen((NaniteModifierScreenHandler) handler, inventory, title));

    private final ScreenHandlerType<? extends ScreenHandler> screenHandlerType;
    private final HandledScreens.Provider provider;

    ModHandledScreens(ScreenHandlerType<? extends ScreenHandler> screenHandlerType, HandledScreens.Provider provider) {
        this.screenHandlerType = screenHandlerType;
        this.provider = provider;
    }

    public ScreenHandlerType<? extends ScreenHandler> getScreenHandlerType() {
        return this.screenHandlerType;
    }

    public HandledScreens.Provider getProvider() {
        return this.provider;
    }
}
