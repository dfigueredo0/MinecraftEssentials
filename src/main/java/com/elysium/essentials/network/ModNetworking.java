package com.elysium.essentials.network;

import com.elysium.essentials.util.BlockBreakUtils;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class ModNetworking {
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("essentials")
                .playToServer(HarvestAllTriggerPacket.TYPE, HarvestAllTriggerPacket.CODEC,
                        ((packet, context) -> {
                            if (context.player() instanceof ServerPlayer player) {
                               HarvestAllStatus.set(player, packet.enabled());
                            }
                        }));
    }
}
