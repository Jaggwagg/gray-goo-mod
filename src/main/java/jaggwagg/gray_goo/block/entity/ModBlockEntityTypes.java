package jaggwagg.gray_goo.block.entity;

import jaggwagg.gray_goo.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

import java.util.Locale;

public enum ModBlockEntityTypes {
    GRAY_GOO(FabricBlockEntityTypeBuilder.create(GrayGooBlockEntity::new, ModBlocks.GRAY_GOO.getBlock()).build());

    private final String id;
    private final BlockEntityType<? extends BlockEntity> getBlockEntityType;

    ModBlockEntityTypes(BlockEntityType<? extends BlockEntity> getBlockEntityType) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.getBlockEntityType = getBlockEntityType;
    }

    public String getId() {
        return this.id;
    }

    public BlockEntityType<? extends BlockEntity> getBlockEntityType() {
        return this.getBlockEntityType;
    }
}
