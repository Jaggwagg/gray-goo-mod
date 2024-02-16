package jaggwagg.gray_goo.item;

import jaggwagg.gray_goo.block.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Locale;

public enum ModItemGroups {
    GRAY_GOO(ModBlocks.GRAY_GOO.getBlock().asItem());

    private final String id;
    private final Item itemGroupItem;
    private final RegistryKey<ItemGroup> itemGroup;

    ModItemGroups(Item itemGroupItem) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.itemGroupItem = itemGroupItem;
        this.itemGroup = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(this.id));
    }

    public String getId() {
        return this.id;
    }

    public Item getItemGroupItem() {
        return this.itemGroupItem;
    }

    public RegistryKey<ItemGroup> getItemGroup() {
        return this.itemGroup;
    }
}
