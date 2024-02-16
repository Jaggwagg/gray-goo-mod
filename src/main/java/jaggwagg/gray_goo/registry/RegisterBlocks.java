package jaggwagg.gray_goo.registry;

import jaggwagg.gray_goo.GrayGoo;
import jaggwagg.gray_goo.block.ModBlocks;
import jaggwagg.gray_goo.item.ModItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class RegisterBlocks {
    public static void init() {
        Arrays.stream(ModBlocks.values()).forEach(value -> registerBlockWithItem(value.getId(), value.getBlock()));
    }

    private static void registerBlockWithItem(String id, Block block) {
        Registry.register(Registries.BLOCK, new Identifier(GrayGoo.MOD_ID, id), block);
        BlockItem item = Registry.register(Registries.ITEM, new Identifier(GrayGoo.MOD_ID, id), new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.GRAY_GOO.getItemGroup()).register(content -> content.add(item));
    }
}
