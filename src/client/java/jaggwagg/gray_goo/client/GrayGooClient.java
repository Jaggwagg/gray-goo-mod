package jaggwagg.gray_goo.client;

import jaggwagg.gray_goo.GrayGoo;
import jaggwagg.gray_goo.block.ModBlocks;
import jaggwagg.gray_goo.block.entity.GrayGooBlockEntity;
import jaggwagg.gray_goo.client.register.RegisterHandledScreens;
import jaggwagg.gray_goo.item.ModTraitItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;

@Environment(EnvType.CLIENT)
public class GrayGooClient implements ClientModInitializer {
    private static void registerColorProviders() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            int color = 0xffffff;

            if (world != null) {
                BlockEntity blockEntity = world.getBlockEntity(pos);

                if (blockEntity instanceof GrayGooBlockEntity grayGooBlockEntity) {
                    NbtCompound nbt = grayGooBlockEntity.createNbt();

                    for (ModTraitItems trait : ModTraitItems.values()) {
                        if (nbt.getBoolean(trait.getId())) {
                            color += trait.getColor();
                        }
                    }
                }
            }

            return color;
        }, ModBlocks.GRAY_GOO.getBlock());

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            Integer color = 0xffffff;
            NbtCompound nbt = stack.getSubNbt("BlockEntityTag");

            if (nbt != null) {
                for (ModTraitItems trait : ModTraitItems.values()) {
                    if (nbt.getBoolean(trait.getId())) {
                        color += trait.getColor();
                    }
                }
            }

            return color;
        }, ModBlocks.GRAY_GOO.getBlock().asItem());
    }

    @Override
    public void onInitializeClient() {
        RegisterHandledScreens.init();
        registerColorProviders();
        GrayGoo.loggerInfo(": Initialized client successfully");
    }
}
