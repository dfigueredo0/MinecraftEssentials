package com.elysium.essentials.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class ModNetworking {
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("essentials")
                .playToServer(HarvestAllTogglePacket.TYPE, HarvestAllTogglePacket.CODEC,
                        ((packet, context) -> {
                            if (context.player() instanceof ServerPlayer player) {
                                HarvestAllStatus.set(player, packet.enabled());
                            }
                        }));
    }
}
