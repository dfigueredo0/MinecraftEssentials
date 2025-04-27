package com.elysium.essentials.client.event;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.client.ModKeyMappings;
import com.elysium.essentials.network.packet.HarvestAllTriggerPacket;
import com.elysium.essentials.util.RecipeBookHandler;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.slf4j.Logger;

@EventBusSubscriber(modid = Essentials.MODID, value = Dist.CLIENT)
public class KeyInputEvent {
    private static boolean lastState = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null || client.level == null) return;

        boolean current = ModKeyMappings.HARVEST_ALL_KEY.isDown();

        if (current != lastState) {
            var connection = client.getConnection();
            if (connection != null) {
                connection.send(new HarvestAllTriggerPacket(current));
            }
            lastState = current;
        }
    }

    @SubscribeEvent
    public static void onScreenKeyPressed(ScreenEvent.KeyPressed.Pre event) {
        Minecraft client = Minecraft.getInstance();

        if (ModKeyMappings.SHOW_RECIPE_BOOK_KEY.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            RecipeBookHandler.setHidden(!RecipeBookHandler.getHidden());
            RecipeBookHandler.updateVisibility(client);
        }
    }
}
