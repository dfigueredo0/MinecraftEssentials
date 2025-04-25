package com.elysium.essentials.client.event;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.client.ModKeyMappings;
import com.elysium.essentials.network.HarvestAllTriggerPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Essentials.MODID, value = Dist.CLIENT)
public class KeyInputEvent {
    private static boolean lastState = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.level == null) return;

        boolean current = ModKeyMappings.HARVEST_ALL_KEY.isDown();

        if (current != lastState) {
            var connection = mc.getConnection();
            if (connection != null) {
                connection.send(new HarvestAllTriggerPacket(current));
            }
            lastState = current;
        }

        // Optional: Check if a block is targeted (for debug or future use)
        HitResult hit = mc.hitResult;
        if (hit instanceof BlockHitResult blockHit) {
            BlockPos targetedPos = blockHit.getBlockPos();
            // You can use targetedPos here if needed
        }
    }
}
