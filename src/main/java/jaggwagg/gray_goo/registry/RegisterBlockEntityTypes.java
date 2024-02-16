package jaggwagg.gray_goo.registry;

import jaggwagg.gray_goo.GrayGoo;
import jaggwagg.gray_goo.block.entity.ModBlockEntityTypes;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class RegisterBlockEntityTypes {
    public static void init() {
        Arrays.stream(ModBlockEntityTypes.values()).forEach(value -> registerBlockEntity(value.getId(), value.getBlockEntityType()));
    }

    private static void registerBlockEntity(String id, BlockEntityType<? extends BlockEntity> blockEntityType) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(GrayGoo.MOD_ID, id), blockEntityType);
    }
}
