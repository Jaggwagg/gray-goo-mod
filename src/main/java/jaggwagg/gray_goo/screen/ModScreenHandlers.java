package jaggwagg.gray_goo.screen;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import java.util.Locale;

public enum ModScreenHandlers {
    NANITE_MODIFIER(new ScreenHandlerType<>(NaniteModifierScreenHandler::new, null));

    private final String id;
    private final ScreenHandlerType<? extends ScreenHandler> screenHandlerType;

    ModScreenHandlers(ScreenHandlerType<? extends ScreenHandler> screenHandlerType) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.screenHandlerType = screenHandlerType;
    }

    public String getId() {
        return this.id;
    }

    public ScreenHandlerType<? extends ScreenHandler> getScreenHandlerType() {
        return this.screenHandlerType;
    }
}
