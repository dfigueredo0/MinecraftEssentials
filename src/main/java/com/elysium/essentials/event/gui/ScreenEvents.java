package com.elysium.essentials.event.gui;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.client.gui.screens.inventory.ExpandedInventoryScreen;
import com.elysium.essentials.util.RecipeBookHandler;
import com.elysium.essentials.world.inventory.ExpandedInventoryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = Essentials.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ScreenEvents {
    @SubscribeEvent
    public static void onGUIEvent(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen || event.getScreen() instanceof CraftingScreen) {
            RecipeBookHandler.toggleRecipeBook(Minecraft.getInstance(), event.getScreen());
        }
    }

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null)
            return;

        if (client.gameMode.hasInfiniteItems())
            return;

        if (event.getScreen() instanceof InventoryScreen) {
            event.setNewScreen(new ExpandedInventoryScreen(
                    new ExpandedInventoryMenu(0, client.player.getInventory(), client.player),
                    client.player.getInventory(),
                    Component.literal("Crafting")
            ));
            Essentials.LOGGER.info("Expanded Inventory Opened");
        }
    }

    @SubscribeEvent
    public static void onScreenClose(ScreenEvent.Closing event) {
        RecipeBookHandler.unregisterButton(event.getScreen());
    }
}
