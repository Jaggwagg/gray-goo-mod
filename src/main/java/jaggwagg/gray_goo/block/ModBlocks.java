package jaggwagg.gray_goo.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;

import java.util.Locale;

public enum ModBlocks {
    GRAY_GOO(new GrayGooBlock(FabricBlockSettings.create().ticksRandomly().breakInstantly())),
    NANITE_MODIFIER(new NaniteModifierBlock(FabricBlockSettings.create().strength(2.0f).requiresTool()));

    private final String id;
    private final Block block;

    <T extends Block> ModBlocks(T block) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.block = block;
    }

    public String getId() {
        return this.id;
    }

    public Block getBlock() {
        return this.block;
    }
}
