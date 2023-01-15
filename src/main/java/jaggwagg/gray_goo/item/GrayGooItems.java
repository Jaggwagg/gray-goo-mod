package jaggwagg.gray_goo.item;

import jaggwagg.gray_goo.GrayGoo;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class GrayGooItems {
    public static final ArrayList<String> TRAIT_KEYS = new ArrayList<>();

    public static void init() {
        Arrays.stream(Items.values()).forEach(value -> registerItem(value.item, value.name));
        Arrays.stream(Traits.values()).forEach(value -> registerItem(value.item, value.name));
        Arrays.stream(Traits.values()).forEach(value -> {
            String string = value.toString().toLowerCase();
            int end = string.indexOf("_");
            String traitString = string.substring(0, end);

            TRAIT_KEYS.add(traitString);
        });
    }

    public enum Items {
        MOLECULAR_LOGIC_GATE(new Item(new Item.Settings())),
        MOLECULAR_REPLICATOR(new Item(new Item.Settings())),
        MOLECULAR_SWITCH(new Item(new Item.Settings())),
        NANITE_TRAIT(new Item(new Item.Settings()));

        public final String name;
        public final Item item;

        <T extends Item> Items(T item) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.item = item;
        }
    }

    public enum Traits {
        BIOLOGICAL_NANITE_TRAIT(new Item(new Item.Settings()), 0xddffdd),
        BROKEN_NANITE_TRAIT(new Item(new Item.Settings()), 0xdddddd),
        CORRUPTED_NANITE_TRAIT(new Item(new Item.Settings()), 0xcccccc),
        EXPLOSIVE_NANITE_TRAIT(new Item(new Item.Settings()), 0xffdddd),
        FLUID_NANITE_TRAIT(new Item(new Item.Settings()), 0xddddff),
        LINEAR_NANITE_TRAIT(new Item(new Item.Settings()), 0xffeecc),
        RAPID_NANITE_TRAIT(new Item(new Item.Settings()), 0xffcccc),
        SELFDESTRUCT_NANITE_TRAIT(new Item(new Item.Settings()), 0xffcccc),
        SOLID_NANITE_TRAIT(new Item(new Item.Settings()), 0xffeedd),
        TAINTED_NANITE_TRAIT(new Item(new Item.Settings()), 0xffddff);

        public final String name;
        public final Item item;
        public final Integer color;

        <T extends Item> Traits(T item, Integer color) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.item = item;
            this.color = color;
        }
    }

    public static void registerItem(Item item, String name) {
        Registry.register(Registries.ITEM, new Identifier(GrayGoo.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(GrayGoo.ITEM_GROUP).register(content -> content.add(item));
    }
}
