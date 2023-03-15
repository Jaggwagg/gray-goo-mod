package jaggwagg.gray_goo.screen;

import jaggwagg.gray_goo.GrayGoo;
import jaggwagg.gray_goo.block.GrayGooBlocks;
import jaggwagg.gray_goo.item.GrayGooItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NaniteModifierScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final List<Item> availableTraits;
    private final PlayerEntity player;
    private final Slot grayGooInputSlot;
    private final Slot naniteTraitInputSlot;
    private final Slot outputSlot;
    private final Inventory input = new SimpleInventory(2) {
        public void markDirty() {
            super.markDirty();
            NaniteModifierScreenHandler.this.onContentChanged(this);
        }
    };
    private final CraftingResultInventory output = new CraftingResultInventory();

    public NaniteModifierScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, ScreenHandlerContext.EMPTY);
    }

    public NaniteModifierScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(GrayGooScreenHandlers.NANITE_ASSEMBLER, syncId);
        this.context = context;
        this.availableTraits = new ArrayList<>();
        this.player = playerInventory.player;
        this.grayGooInputSlot = this.addSlot(new GrayGooInputSlot(this.input, 0, 20, 20));
        this.naniteTraitInputSlot = this.addSlot(new NaniteTraitInputSlot(this.input, 1, 20,48));
        this.outputSlot = this.addSlot(new GrayGooOutputSlot(this.output, 1, 143, 33));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

    }

    private void updateResult() {
        this.availableTraits.clear();
        this.outputSlot.setStack(ItemStack.EMPTY);

        if (this.input.getStack(0).isEmpty() || this.input.getStack(0).isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
        }

        if (this.input.getStack(0).isOf(GrayGooBlocks.Blocks.GRAY_GOO.block.asItem())) {
            NbtCompound nbt = this.input.getStack(0).getSubNbt("BlockEntityTag");

            if (nbt == null) {
                int numOfItems = this.input.getStack(0).getCount();
                NbtCompound newNbt = new NbtCompound();

                newNbt.put("BlockEntityTag", new NbtCompound());

                Arrays.stream(GrayGooItems.Traits.values()).forEach(value -> {
                    String string = value.toString().toLowerCase();
                    int end = string.indexOf("_");
                    String traitString = string.substring(0, end);

                    newNbt.getCompound("BlockEntityTag").putBoolean(traitString, false);
                });

                newNbt.getCompound("BlockEntityTag").putString("id", GrayGoo.MOD_ID + ":gray_goo_block_entity");
                newNbt.getCompound("BlockEntityTag").putInt("age", 0);
                newNbt.getCompound("BlockEntityTag").putInt("growthSize", 2);

                ItemStack outputStack = new ItemStack(GrayGooBlocks.Blocks.GRAY_GOO.block, numOfItems);
                outputStack.setNbt(newNbt);
                this.input.setStack(0, outputStack);
            } else {
                Arrays.stream(GrayGooItems.Traits.values()).forEach(value -> {
                    String string = value.toString().toLowerCase();
                    int end = string.indexOf("_");
                    String traitString = string.substring(0, end);

                    if (nbt.getBoolean(traitString)) {
                        this.availableTraits.add(value.item);
                    }

                    if (this.input.getStack(1).isOf(value.item)) {
                        if (!nbt.getBoolean(traitString)) {
                            ItemStack outputStack = new ItemStack(GrayGooBlocks.Blocks.GRAY_GOO.block);
                            NbtCompound newNbt = new NbtCompound();
                            newNbt.copyFrom(nbt);
                            newNbt.putBoolean(traitString, true);
                            outputStack.setSubNbt("BlockEntityTag", newNbt);
                            this.output.setStack(0, outputStack);
                        }
                    }
                });
            }
        }
    }


    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.input) {
            this.updateResult();
        }

    }

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.input);
        });
    }

    public void onTakeOutput() {
        this.input.getStack(0).decrement(1);
        this.input.getStack(1).decrement(1);
        this.output.getStack(0).decrement(1);
        updateResult();
    }

    public List<Item> getAvailableTraits() {
        return this.availableTraits;
    }

    public int getAvailableRecipeCount() {
        return this.availableTraits.size();
    }

    @Override
    public boolean canUse(PlayerEntity playerEntity) {
        return canUse(this.context, player, GrayGooBlocks.Blocks.NANITE_MODIFIER.block);
    }

    private boolean isUsableAsAddition() {
        return false;
    }

    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();

            if (index == 2) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index != 0 && index != 1) {
                if (index >= 3 && index < 39) {
                    int i = this.isUsableAsAddition() ? 1 : 0;
                    if (!this.insertItem(itemStack2, i, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    private static class GrayGooInputSlot extends Slot {
        public GrayGooInputSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return matches(stack);
        }

        public static boolean matches(ItemStack stack) {
            return stack.isOf(GrayGooBlocks.Blocks.GRAY_GOO.block.asItem());
        }
    }

    private static class NaniteTraitInputSlot extends Slot {
        public NaniteTraitInputSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return matches(stack);
        }

        public static boolean matches(ItemStack stack) {
            for (GrayGooItems.Traits value : GrayGooItems.Traits.values()) {
                if (stack.isOf(value.item)) {
                    return true;
                }
            }

            return false;
        }
    }

    class GrayGooOutputSlot extends Slot {
        public GrayGooOutputSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return true;
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {
            NaniteModifierScreenHandler.this.onTakeOutput();
        }
    }
}
