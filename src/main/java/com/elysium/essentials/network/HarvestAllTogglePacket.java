package com.elysium.essentials.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public record HarvestAllTogglePacket(boolean enabled) implements CustomPacketPayload {
    public static final Type<HarvestAllTogglePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("essentials", "harvest_all_toggle"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HarvestAllTogglePacket> CODEC =
            StreamCodec.of(
                    (buf, packet) -> buf.writeBoolean(packet.enabled),
                    buf -> new HarvestAllTogglePacket(buf.readBoolean())
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
