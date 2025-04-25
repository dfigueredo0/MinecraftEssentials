package com.elysium.essentials.event.gui;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.client.ModKeyMappings;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Essentials.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RecipeBookEvent {
    @SubscribeEvent
    public static void onGUIEvent() {
        Minecraft client = Minecraft.getInstance();

        boolean state = ModKeyMappings.SHOW_RECIPE_BOOK_KEY.consumeClick();
    }
}
