package com.elysium.essentials.world.inventory;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ExpandedInventoryMenu extends RecipeBookMenu {
    public ExpandedInventoryMenu(MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Override
    public PostPlaceAction handlePlacement(boolean useMaxItems, boolean isCreative, RecipeHolder<?> recipe, ServerLevel level, Inventory playerInventory) {
        return null;
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {

    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return null;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
