package jaggwagg.gray_goo.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import jaggwagg.gray_goo.block.tag.ModBlockTags;

public class ModTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModBlockTags.BIOLOGICAL_BLOCKS.getTagKey()).add(
                Blocks.ACACIA_LEAVES,
                Blocks.ACACIA_LOG,
                Blocks.ACACIA_STAIRS,
                Blocks.AZALEA,
                Blocks.AZALEA_LEAVES,
                Blocks.BIRCH_LEAVES,
                Blocks.BIRCH_LOG,
                Blocks.BIRCH_STAIRS,
                Blocks.CARVED_PUMPKIN,
                Blocks.CHERRY_LEAVES,
                Blocks.CHERRY_LOG,
                Blocks.COARSE_DIRT,
                Blocks.DARK_OAK_LEAVES,
                Blocks.DARK_OAK_LOG,
                Blocks.DARK_OAK_STAIRS,
                Blocks.DIRT,
                Blocks.DIRT_PATH,
                Blocks.FARMLAND,
                Blocks.FLOWERING_AZALEA,
                Blocks.GRASS_BLOCK,
                Blocks.JUNGLE_LEAVES,
                Blocks.JUNGLE_LOG,
                Blocks.JUNGLE_STAIRS,
                Blocks.MANGROVE_LEAVES,
                Blocks.MANGROVE_LOG,
                Blocks.MANGROVE_STAIRS,
                Blocks.MOSS_BLOCK,
                Blocks.MUD,
                Blocks.MUDDY_MANGROVE_ROOTS,
                Blocks.OAK_LEAVES,
                Blocks.OAK_LOG,
                Blocks.OAK_STAIRS,
                Blocks.PODZOL,
                Blocks.PUMPKIN,
                Blocks.ROOTED_DIRT,
                Blocks.SPRUCE_LEAVES,
                Blocks.SPRUCE_LOG,
                Blocks.SPRUCE_STAIRS,
                Blocks.STRIPPED_ACACIA_LOG,
                Blocks.STRIPPED_BIRCH_LOG,
                Blocks.STRIPPED_CHERRY_LOG,
                Blocks.STRIPPED_DARK_OAK_LOG,
                Blocks.STRIPPED_JUNGLE_LOG,
                Blocks.STRIPPED_MANGROVE_LOG,
                Blocks.STRIPPED_OAK_LOG,
                Blocks.STRIPPED_SPRUCE_LOG
        );

        getOrCreateTagBuilder(ModBlockTags.BLACKLISTED_BLOCKS.getTagKey()).add(
                Blocks.BEDROCK
        );

        getOrCreateTagBuilder(ModBlockTags.FLUID_BLOCKS.getTagKey()).add(
                Blocks.BUBBLE_COLUMN,
                Blocks.KELP,
                Blocks.KELP_PLANT,
                Blocks.LAVA,
                Blocks.SEAGRASS,
                Blocks.SEA_PICKLE,
                Blocks.TALL_SEAGRASS,
                Blocks.WATER
        );

        getOrCreateTagBuilder(ModBlockTags.SOLID_BLOCKS.getTagKey()).add(
                Blocks.ANDESITE,
                Blocks.ANCIENT_DEBRIS,
                Blocks.BASALT,
                Blocks.BLACKSTONE,
                Blocks.COAL_ORE,
                Blocks.COPPER_ORE,
                Blocks.COBBLESTONE,
                Blocks.COBBLED_DEEPSLATE,
                Blocks.DEEPSLATE,
                Blocks.DEEPSLATE_COAL_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.DIAMOND_ORE,
                Blocks.DIORITE,
                Blocks.EMERALD_ORE,
                Blocks.GOLD_ORE,
                Blocks.GRAVEL,
                Blocks.GRANITE,
                Blocks.IRON_ORE,
                Blocks.LAPIS_ORE,
                Blocks.NETHERRACK,
                Blocks.REDSTONE_ORE,
                Blocks.SAND,
                Blocks.SANDSTONE,
                Blocks.STONE,
                Blocks.TUFF
        );
    }
}
