package com.elysium.essentials.network.status;

import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HarvestAllStatus {
    private static final ConcurrentHashMap<UUID, Boolean> states = new ConcurrentHashMap<>();

    public static boolean isEnabled(ServerPlayer player) {
        return states.getOrDefault(player.getUUID(), false);
    }

    public static void set(ServerPlayer player, boolean enabled) {
        states.put(player.getUUID(), enabled);
    }

    public static void clear(ServerPlayer player) {
        states.remove(player.getUUID());
    }
}
