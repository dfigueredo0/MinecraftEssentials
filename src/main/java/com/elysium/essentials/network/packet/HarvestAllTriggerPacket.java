package com.elysium.essentials.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public record HarvestAllTriggerPacket(boolean enabled) implements CustomPacketPayload {
    public static final Type<HarvestAllTriggerPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("essentials", "harvest_all_trigger"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HarvestAllTriggerPacket> CODEC =
            StreamCodec.of(
                    (buf, packet) -> buf.writeBoolean(packet.enabled),
                    buf -> new HarvestAllTriggerPacket(buf.readBoolean())
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
