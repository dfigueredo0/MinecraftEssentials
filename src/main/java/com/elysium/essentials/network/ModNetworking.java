package com.elysium.essentials.network;

import com.elysium.essentials.network.packet.HarvestAllTriggerPacket;
import com.elysium.essentials.network.packet.ShowRecipeBookTogglePacket;
import com.elysium.essentials.network.status.HarvestAllStatus;
import com.elysium.essentials.network.status.ShowRecipeBookStatus;
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
                        }))
                .playToServer(ShowRecipeBookTogglePacket.TYPE, ShowRecipeBookTogglePacket.CODEC, (packet, context) -> {
                    if (context.player() instanceof ServerPlayer player) {
                        ShowRecipeBookStatus.set(player, packet.enabled());
                    }
                });
    }
}
