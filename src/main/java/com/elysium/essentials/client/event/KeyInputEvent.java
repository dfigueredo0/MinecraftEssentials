package com.elysium.essentials.client.event;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.client.ModKeyMappings;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Essentials.MODID, value = Dist.CLIENT)
public class KeyInputEvent {
    private static boolean harvestAllEnabled = false;

    public static boolean isHarvestAllEnabled() {
        return harvestAllEnabled;
    }

    @SubscribeEvent
    public static void onKeyTick(ClientTickEvent event) {
        if (ModKeyMappings.HARVEST_ALL_KEY.consumeClick()) {
            harvestAllEnabled = !harvestAllEnabled;
        }
    }
}
