package jaggwagg.gray_goo.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import java.util.Locale;

public enum ModTraitItems {
    BIOLOGICAL_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xddffdd),
    BROKEN_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xdddddd),
    CORRUPTED_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xcccccc),
    EXPLOSIVE_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xffdddd),
    FLUID_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xddddff),
    LINEAR_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xffeecc),
    RAPID_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xffcccc),
    SELF_DESTRUCT_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xffcccc),
    SOLID_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xffeedd),
    TAINTED_NANITE_TRAIT(new Item(new FabricItemSettings()), 0xffddff);

    private final String id;
    private final Item item;
    private final Integer color;

    <T extends Item> ModTraitItems(T item, Integer color) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.item = item;
        this.color = color;
    }

    public String getId() {
        return this.id;
    }

    public Item getItem() {
        return this.item;
    }

    public Integer getColor() {
        return this.color;
    }
}
