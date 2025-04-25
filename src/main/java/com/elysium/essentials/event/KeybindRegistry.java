package com.elysium.essentials.event;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.client.ModKeyMappings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Essentials.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class KeybindRegistry {
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.HARVEST_ALL_KEY);
        event.register(ModKeyMappings.SORT_INV_KEY);
        event.register(ModKeyMappings.SHOW_RECIPE_BOOK_KEY);
    }
}
