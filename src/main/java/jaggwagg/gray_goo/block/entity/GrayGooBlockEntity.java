package jaggwagg.gray_goo.block.entity;

import jaggwagg.gray_goo.item.ModTraitItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GrayGooBlockEntity extends BlockEntity {
    private int age = 0;
    private int growthSize = 2;
    private Map<String, Boolean> traits = new HashMap<>();

    public GrayGooBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.GRAY_GOO.getBlockEntityType(), pos, state);

        this.resetAllTraits();
    }

    public int getGrowthSize() {
        return this.growthSize;
    }

    public boolean getTrait(String key) {
        return this.traits.get(key);
    }

    public Map<String, Boolean> getAllTraits() {
        return this.traits;
    }

    public void setAllTraits(Map<String, Boolean> traits) {
        this.traits = traits;
    }

    public void resetAllTraits() {
        Arrays.stream(ModTraitItems.values()).forEach(trait -> this.traits.put(trait.getId(), false));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        age = nbt.getInt("age");
        growthSize = nbt.getInt("growthSize");

        Arrays.stream(ModTraitItems.values()).forEach(trait -> this.traits.put(trait.getId(), nbt.getBoolean(trait.getId())));
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("age", age);
        nbt.putInt("growthSize", growthSize);
        traits.forEach(nbt::putBoolean);

        super.writeNbt(nbt);
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
