package com.elysium.essentials.network;

import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HarvestAllStatus {
    private static final ConcurrentHashMap<UUID, Boolean> toggleStates = new ConcurrentHashMap<>();

    public static boolean isEnabled(ServerPlayer player) {
        return toggleStates.getOrDefault(player.getUUID(), false);
    }

    public static void set(ServerPlayer player, boolean enabled) {
        toggleStates.put(player.getUUID(), enabled);
    }

    public static void clear(ServerPlayer player) {
        toggleStates.remove(player.getUUID());
    }
}
