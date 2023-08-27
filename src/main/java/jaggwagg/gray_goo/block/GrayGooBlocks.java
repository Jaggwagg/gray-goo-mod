package jaggwagg.gray_goo.block;

import jaggwagg.gray_goo.GrayGoo;
import jaggwagg.gray_goo.block.entity.GrayGooBlockEntity;
import jaggwagg.gray_goo.item.GrayGooItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public class GrayGooBlocks {
    public static BlockEntityType<GrayGooBlockEntity> GRAY_GOO_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(GrayGoo.MOD_ID, "gray_goo_block_entity"), FabricBlockEntityTypeBuilder.create(GrayGooBlockEntity::new, Blocks.GRAY_GOO.block).build(null));

    public static void init() {
        Arrays.stream(Blocks.values()).forEach(value -> registerBlock(value.block, value.name));
    }

    public enum Blocks {
        EMP_SWITCH(new EMPSwitchBlock(FabricBlockSettings.create().strength(2.0f).requiresTool())),
        GRAY_GOO(new GrayGooBlock(FabricBlockSettings.create().ticksRandomly().breakInstantly())),
        NANITE_MODIFIER(new NaniteModifierBlock(FabricBlockSettings.create().strength(2.0f).requiresTool()));

        public final String name;
        public final Block block;

        <T extends Block> Blocks(T block) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.block = block;
        }
    }

    public static void registerBlock(Block block, String name) {
        Registry.register(Registries.BLOCK, new Identifier(GrayGoo.MOD_ID, name), block);
        BlockItem item = Registry.register(Registries.ITEM, new Identifier(GrayGoo.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(GrayGooItems.ITEM_GROUP).register(content -> content.add(item));
    }
}
