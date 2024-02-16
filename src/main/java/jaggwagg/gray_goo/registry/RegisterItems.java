package jaggwagg.gray_goo.registry;

import jaggwagg.gray_goo.GrayGoo;
import jaggwagg.gray_goo.item.ModItemGroups;
import jaggwagg.gray_goo.item.ModItems;
import jaggwagg.gray_goo.item.ModTraitItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class RegisterItems {
    public static void init() {
        RegistryKey<ItemGroup> modItemGroupGeneral = ModItemGroups.GRAY_GOO.getItemGroup();

        Arrays.stream(ModItems.values()).forEach(value -> registerItem(value.getId(), value.getItem(), modItemGroupGeneral));
        Arrays.stream(ModTraitItems.values()).forEach(value -> registerItem(value.getId(), value.getItem(), modItemGroupGeneral));
    }

    private static void registerItem(String id, Item item, RegistryKey<ItemGroup> itemGroup) {
        Registry.register(Registries.ITEM, new Identifier(GrayGoo.MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(item));
    }
}
