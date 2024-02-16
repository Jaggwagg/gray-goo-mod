package jaggwagg.gray_goo.block.tag;

import jaggwagg.gray_goo.GrayGoo;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Locale;

public enum ModBlockTags {
    BIOLOGICAL_BLOCKS(),
    BLACKLISTED_BLOCKS(),
    FLUID_BLOCKS(),
    SOLID_BLOCKS();

    private final String id;
    private final TagKey<Block> tagKey;

    ModBlockTags() {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.tagKey = TagKey.of(RegistryKeys.BLOCK, new Identifier(GrayGoo.MOD_ID, id));
    }

    public String getId() {
        return this.id;
    }

    public TagKey<Block> getTagKey() {
        return this.tagKey;
    }
}
