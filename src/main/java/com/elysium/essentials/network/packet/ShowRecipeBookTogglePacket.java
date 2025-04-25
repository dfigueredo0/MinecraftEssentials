package com.elysium.essentials.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ShowRecipeBookTogglePacket(boolean enabled) implements CustomPacketPayload {
    public static final Type<ShowRecipeBookTogglePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("essentials", "show_recipe_book_toggle"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ShowRecipeBookTogglePacket> CODEC =
            StreamCodec.of(
                    (buf, packet) -> buf.writeBoolean(packet.enabled),
                    buf -> new ShowRecipeBookTogglePacket(buf.readBoolean())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

