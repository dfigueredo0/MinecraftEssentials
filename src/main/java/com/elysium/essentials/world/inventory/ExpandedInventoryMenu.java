package com.elysium.essentials.world.inventory;

import com.elysium.essentials.Essentials;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExpandedInventoryMenu extends AbstractCraftingMenu {
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COL_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COL_COUNT + PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 1;
    private static final int SHIFT_X = 24;

    public ExpandedInventoryMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        super(ModMenuTypes.EXPANDED_INVENTORY.get(), containerId, 2, 2);

        for (int i = 0; i < 4; ++i) {
            int x = 8 + SHIFT_X;
            int y = 8 + i * 18;
            this.addSlot(new Slot(playerInventory, 39 - i, x, y));
        }

        addCraftingGridSlots(98 + SHIFT_X, 18);

        addStandardInventorySlots(playerInventory, 8 + SHIFT_X, 84);
        this.addSlot(new ElytraSlot(playerInventory, 39, 6, 26));
    }

    public ExpandedInventoryMenu(int containerId, Inventory playerInventory, Player player) {
        this(containerId, playerInventory, (RegistryFriendlyByteBuf) null);
    }

    @Override
    public PostPlaceAction handlePlacement(boolean useMaxItems, boolean isCreative, RecipeHolder<?> recipe, ServerLevel level, Inventory playerInventory) {
        return null;
    }

    @Override
    public Slot getResultSlot() {
        return null;
    }

    @Override
    public List<Slot> getInputGridSlots() {
        return List.of();
    }

    @Override
    protected Player owner() {
        return null;
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {

    }


    @Override
    public @NotNull RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem())
            return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copySourceStack = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false))
                return ItemStack.EMPTY;
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false))
                return ItemStack.EMPTY;
        } else {
            Essentials.LOGGER.error("Invalid Slot Index: {}", index);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0)
            sourceSlot.set(ItemStack.EMPTY);
        else
            sourceSlot.setChanged();

        sourceSlot.onTake(player, sourceStack);
        return copySourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
