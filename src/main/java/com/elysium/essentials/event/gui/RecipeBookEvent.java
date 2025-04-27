package com.elysium.essentials.event.gui;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.util.RecipeBookHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = Essentials.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RecipeBookEvent {
    @SubscribeEvent
    public static void onGUIEvent(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen || event.getScreen() instanceof CraftingScreen) {
            RecipeBookHandler.toggleRecipeBook(Minecraft.getInstance(), event.getScreen());
        }
    }

    @SubscribeEvent
    public static void onScreenClose(ScreenEvent.Closing event) {
        RecipeBookHandler.unregisterButton(event.getScreen());
    }
}
