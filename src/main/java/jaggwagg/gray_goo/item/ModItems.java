package jaggwagg.gray_goo.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import java.util.Locale;

public enum ModItems {
    MOLECULAR_LOGIC_GATE(new Item(new FabricItemSettings())),
    MOLECULAR_REPLICATOR(new Item(new FabricItemSettings())),
    MOLECULAR_SWITCH(new Item(new FabricItemSettings())),
    NANITE_TRAIT(new Item(new FabricItemSettings()));

    private final String id;
    private final Item item;

    <T extends Item> ModItems(T item) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.item = item;
    }

    public String getId() {
        return this.id;
    }

    public Item getItem() {
        return this.item;
    }
}
