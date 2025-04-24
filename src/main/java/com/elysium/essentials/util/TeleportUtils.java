package com.elysium.essentials.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class TeleportUtils {
    //TODO: Add a Server Config to make this be able to change depending on Config number
    private static final long TIMEOUT_SECONDS = 60;

    public static final Map<UUID, UUID> tpaRequests = new ConcurrentHashMap<>(); // target -> requester
    public static final Map<UUID, UUID> tpaHereRequests = new ConcurrentHashMap<>();
    private static final Map<UUID, ScheduledFuture<?>> requestTimeouts = new ConcurrentHashMap<>();

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void sendTpaRequest(ServerPlayer requester, ServerPlayer target) {
        UUID targetId = target.getUUID();
        tpaRequests.put(targetId, requester.getUUID());

        target.sendSystemMessage(Component.literal(requester.getName().getString() + " has requested to teleport to you. Use /tpaccept or /tpdeny."));

        scheduleTimeout(target, requester, targetId, false);
    }

    public static void sendTpaHereRequest(ServerPlayer requester, ServerPlayer target) {
        UUID targetId = target.getUUID();
        tpaHereRequests.put(targetId, requester.getUUID());

        target.sendSystemMessage(Component.literal(requester.getName().getString() + " has requested you teleport to them. Use /tpaccept or /tpdeny."));

        scheduleTimeout(target, requester, targetId, true);
    }

    private static void scheduleTimeout(ServerPlayer target, ServerPlayer requester, UUID targetId, boolean hereRequest) {
        cancelTimeout(targetId);

        ScheduledFuture<?> future = scheduler.schedule(() -> {
            if (hereRequest) {
                tpaHereRequests.remove(targetId);
            } else {
                tpaRequests.remove(targetId);
            }

            target.sendSystemMessage(Component.literal("Teleport request has expired."));
            requester.sendSystemMessage(Component.literal("Your teleport request to " + target.getName().getString() + " has expired."));

            requestTimeouts.remove(targetId);
        }, TIMEOUT_SECONDS, TimeUnit.SECONDS);

        requestTimeouts.put(targetId, future);
    }

    public static boolean hasValidRequest(UUID target) {
        return tpaRequests.containsKey(target) || tpaHereRequests.containsKey(target);
    }

    public static UUID getTpaRequester(UUID target) {
        return tpaRequests.get(target);
    }

    public static UUID getTpaHereRequester(UUID target) {
        return tpaHereRequests.get(target);
    }

    public static void clearRequest(UUID target) {
        tpaRequests.remove(target);
        tpaHereRequests.remove(target);
        cancelTimeout(target);
    }

    private static void cancelTimeout(UUID targetId) {
        ScheduledFuture<?> future = requestTimeouts.remove(targetId);
        if (future != null) {
            future.cancel(false);
        }
    }
}
