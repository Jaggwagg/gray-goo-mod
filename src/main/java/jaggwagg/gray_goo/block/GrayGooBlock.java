package jaggwagg.gray_goo.block;

import jaggwagg.gray_goo.block.entity.GrayGooBlockEntity;
import jaggwagg.gray_goo.block.tag.ModBlockTags;
import jaggwagg.gray_goo.item.ModTraitItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GrayGooBlock extends Block implements BlockEntityProvider {
    private static final BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    public GrayGooBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(ACTIVATED, false));
    }

    private static Optional<GrayGooBlockEntity> getGrayGooBlockEntity(WorldAccess world, BlockPos blockPos) {
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        if (blockEntity instanceof GrayGooBlockEntity grayGooBlockEntity) {
            return Optional.of(grayGooBlockEntity);
        }

        return Optional.empty();
    }

    private void doMiscEffects(World world, BlockPos blockPos) {
        Optional<GrayGooBlockEntity> grayGooBlockEntity = getGrayGooBlockEntity(world, blockPos);

        if (grayGooBlockEntity.isEmpty()) {
            return;
        }

        if (grayGooBlockEntity.get().getTrait(ModTraitItems.BROKEN_NANITE_TRAIT.getId())) {
            if (world.getBlockState(blockPos).get(ACTIVATED)) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            }
        }

        if (grayGooBlockEntity.get().getTrait(ModTraitItems.EXPLOSIVE_NANITE_TRAIT.getId())) {
            int randomExplosionChance = world.getRandom().nextInt(50);

            if (randomExplosionChance == 0) {
                world.createExplosion(null, world.getDamageSources().generic(), null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2.0f, false, World.ExplosionSourceType.BLOCK);
            }
        }
    }

    private void grow(World world, BlockPos blockPos) {
        Optional<GrayGooBlockEntity> grayGooBlockEntity = getGrayGooBlockEntity(world, blockPos);

        if (grayGooBlockEntity.isEmpty()) {
            return;
        }

        boolean grew;

        if (grayGooBlockEntity.get().getTrait(ModTraitItems.LINEAR_NANITE_TRAIT.getId())) {
            grew = growLinearly(world, blockPos, grayGooBlockEntity.get());
        } else {
            grew = growSpherically(world, blockPos, grayGooBlockEntity.get());
        }

        if (grew) {
            world.setBlockState(blockPos, this.getDefaultState().with(ACTIVATED, true));
        }
    }

    private boolean growLinearly(World world, BlockPos blockPos, GrayGooBlockEntity grayGooBlockEntity) {
        boolean grew = false;

        for (Direction direction : Direction.values()) {
            BlockPos currentBlockPos = blockPos.offset(direction);
            BlockPos oppositeBlockPos = blockPos.offset(direction.getOpposite());

            if (world.getBlockState(currentBlockPos).isOf(this)) {
                if (shouldGrow(world, oppositeBlockPos, grayGooBlockEntity)) {
                    this.placeGooBlock(world, oppositeBlockPos, grayGooBlockEntity);
                    grew = true;
                }
            }
        }

        return grew;
    }

    private boolean growSpherically(World world, BlockPos blockPos, GrayGooBlockEntity grayGooBlockEntity) {
        boolean grew = false;
        int growthSize = grayGooBlockEntity.getGrowthSize();
        int posX = blockPos.getX();
        int posY = blockPos.getY();
        int posZ = blockPos.getZ();

        for (int x = posX - growthSize; x <= posX + growthSize; x++) {
            for (int y = posY - growthSize; y <= posY + growthSize; y++) {
                for (int z = posZ - growthSize; z <= posZ + growthSize; z++) {
                    double distance = (posX - x) * (posX - x) + ((posZ - z) * (posZ - z)) + ((posY - y) * (posY - y));

                    if (distance < growthSize) {
                        BlockPos currentBlockPos = new BlockPos(x, y, z);

                        if (shouldGrow(world, currentBlockPos, grayGooBlockEntity)) {
                            this.placeGooBlock(world, currentBlockPos, grayGooBlockEntity);
                            grew = true;
                        }
                    }
                }
            }
        }

        return grew;
    }

    private boolean shouldGrow(World world, BlockPos blockPos, GrayGooBlockEntity grayGooBlockEntity) {
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.isIn(ModBlockTags.BLACKLISTED_BLOCKS.getTagKey())) {
            return false;
        }

        if (grayGooBlockEntity.getTrait(ModTraitItems.BIOLOGICAL_NANITE_TRAIT.getId())) {
            if (blockState.isIn(ModBlockTags.BIOLOGICAL_BLOCKS.getTagKey())) {
                return true;
            }
        }

        if (grayGooBlockEntity.getTrait(ModTraitItems.FLUID_NANITE_TRAIT.getId())) {
            if (blockState.contains(Properties.WATERLOGGED)) {
                if (blockState.get(Properties.WATERLOGGED)) {
                    return true;
                }
            }

            if (blockState.isIn(ModBlockTags.FLUID_BLOCKS.getTagKey())) {
                return true;
            }
        }

        if (grayGooBlockEntity.getTrait(ModTraitItems.SOLID_NANITE_TRAIT.getId())) {
            if (blockState.isIn(ModBlockTags.SOLID_BLOCKS.getTagKey())) {
                return true;
            }
        }

        if (grayGooBlockEntity.getTrait(ModTraitItems.SELF_DESTRUCT_NANITE_TRAIT.getId())) {
            if (blockState.isOf(this)) {
                Optional<GrayGooBlockEntity> currentGrayGooBlockEntity = getGrayGooBlockEntity(world, blockPos);

                if (currentGrayGooBlockEntity.isPresent()) {
                    return !currentGrayGooBlockEntity.get().getAllTraits().equals(grayGooBlockEntity.getAllTraits());
                }
            }
        }

        return false;
    }

    private void placeGooBlock(World world, BlockPos blockPos, GrayGooBlockEntity grayGooBlockEntity) {
        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        world.setBlockState(blockPos, this.getDefaultState().with(ACTIVATED, true));

        Optional<GrayGooBlockEntity> newGrayGooBlockEntity = getGrayGooBlockEntity(world, blockPos);

        newGrayGooBlockEntity.ifPresent(gooBlockEntity -> gooBlockEntity.setAllTraits(grayGooBlockEntity.getAllTraits()));
    }

    private void poisonEntity(LivingEntity livingEntity) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200));
    }

    private void damageEntity(World world, LivingEntity livingEntity) {
        livingEntity.damage(world.getDamageSources().generic(), 2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        this.grow(world, blockPos);
        this.doMiscEffects(world, blockPos);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GrayGooBlockEntity(pos, state);
    }

    @Override
    public void onSteppedOn(World world, BlockPos blockPos, BlockState state, Entity entity) {
        Optional<GrayGooBlockEntity> grayGooBlockEntity = getGrayGooBlockEntity(world, blockPos);

        if (grayGooBlockEntity.isEmpty()) {
            return;
        }

        if (grayGooBlockEntity.get().getTrait(ModTraitItems.TAINTED_NANITE_TRAIT.getId())) {
            this.grow(world, blockPos);
            this.doMiscEffects(world, blockPos);

            if (entity instanceof LivingEntity livingEntity) {
                damageEntity(world, livingEntity);
                poisonEntity(livingEntity);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        this.grow(world, blockPos);
        this.doMiscEffects(world, blockPos);

        Optional<GrayGooBlockEntity> grayGooBlockEntity = getGrayGooBlockEntity(world, blockPos);

        if (grayGooBlockEntity.isEmpty()) {
            return ActionResult.PASS;
        }

        if (grayGooBlockEntity.get().getTrait(ModTraitItems.TAINTED_NANITE_TRAIT.getId())) {
            poisonEntity(player);
        }

        return ActionResult.CONSUME;
    }

    public void onBreak(World world, BlockPos blockPos, BlockState state, PlayerEntity player) {
        Optional<GrayGooBlockEntity> grayGooBlockEntity = getGrayGooBlockEntity(world, blockPos);

        if (grayGooBlockEntity.isEmpty()) {
            return;
        }

        if (!world.isClient && !player.isCreative() && !world.getBlockState(blockPos).get(ACTIVATED)) {
            ItemStack itemStack = new ItemStack(this);
            grayGooBlockEntity.get().setStackNbt(itemStack);

            ItemEntity itemEntity = new ItemEntity(world, (double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.5, (double) blockPos.getZ() + 0.5, itemStack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }

        if (grayGooBlockEntity.get().getTrait(ModTraitItems.TAINTED_NANITE_TRAIT.getId())) {
            poisonEntity(player);
        }

        super.onBreak(world, blockPos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos blockPos, BlockPos neighborPos) {
        Optional<GrayGooBlockEntity> grayGooBlockEntity = getGrayGooBlockEntity(world, blockPos);

        if (grayGooBlockEntity.isEmpty()) {
            return state;
        }

        if (!grayGooBlockEntity.get().getTrait(ModTraitItems.RAPID_NANITE_TRAIT.getId())) {
            return state;
        }

        if (world.getServer() == null) {
            return state;
        }

        if (world.getServer().getGameRules().getInt(GameRules.RANDOM_TICK_SPEED) == 0) {
            return state;
        }

        int minimumTickDelay = 100;
        int addedTickDelay = 400;
        int tickDelay;

        tickDelay = (minimumTickDelay + world.getRandom().nextInt(addedTickDelay)) / world.getServer().getGameRules().getInt(GameRules.RANDOM_TICK_SPEED);
        world.scheduleBlockTick(blockPos, this, tickDelay);

        return state;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        this.grow(world, blockPos);
        this.doMiscEffects(world, blockPos);
    }
}
