package com.elysium.essentials.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InvSeeMenu extends AbstractContainerMenu {
    public InvSeeMenu(int containerId, Inventory viewer, ServerPlayer target) {
        super(MenuType.GENERIC_9x4, containerId);

        addStandardInventorySlots(target.getInventory(), 8, 18);
        addStandardInventorySlots(viewer, 8, 108);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
