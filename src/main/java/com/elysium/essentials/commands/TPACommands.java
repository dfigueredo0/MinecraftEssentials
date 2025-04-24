package com.elysium.essentials.commands;

import com.elysium.essentials.util.TeleportUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Relative;

import java.util.EnumSet;
import java.util.UUID;

public class TPACommands {
    public TPACommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("tpa")
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(this::sendTPA))
        );

        dispatcher.register(
                Commands.literal("tpaccept")
                        .executes(this::acceptTPA)
        );

        dispatcher.register(
                Commands.literal("tpahere")
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(this::sendTpaHere))
        );

        dispatcher.register(
                Commands.literal("tpdeny")
                        .executes(this::denyTpa)
        );
    }

    private int sendTPA(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer requester = context.getSource().getPlayerOrException();
        ServerPlayer target = EntityArgument.getPlayer(context, "target");

        if (isSelfRequest(requester, target))
            return 1;

        TeleportUtils.sendTpaRequest(requester, target);
        requester.sendSystemMessage(Component.literal("Teleport request sent to " + target.getName().getString()));
        target.sendSystemMessage(Component.literal(requester.getName().getString()
                + " has requested to teleport to you. Use /tpaccept or /tpdeny."));
        return 0;
    }

    private int acceptTPA(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer target = context.getSource().getPlayerOrException();
        UUID targetId = target.getUUID();

        UUID requesterId = TeleportUtils.getTpaRequester(targetId);
        UUID hereRequesterId = TeleportUtils.getTpaHereRequester(targetId);

        if (requesterId == null && hereRequesterId == null) {
            target.sendSystemMessage(Component.literal("No pending teleport requests."));
            return 1;
        }

        ServerPlayer requester = (requesterId != null)
                ? target.server.getPlayerList().getPlayer(requesterId)
                : target.server.getPlayerList().getPlayer(hereRequesterId);

        if (requester == null) {
            context.getSource().sendFailure(Component.literal("No requester found."));
            return -1;
        }

        ServerLevel dimension = target.server.getLevel(
                (requesterId != null) ? target.level().dimension() : requester.level().dimension()
        );

        if (dimension == null) {
            context.getSource().sendFailure(Component.literal("Valid dimension not found."));
            return -1;
        }

        if (requesterId != null) {
            requester.teleportTo(
                    dimension,
                    target.getX(), target.getY(), target.getZ(),
                    EnumSet.noneOf(Relative.class),
                    requester.getYRot(), requester.getXRot(),
                    true
            );
            requester.sendSystemMessage(Component.literal("Teleporting to " + target.getName().getString()));
            target.sendSystemMessage(Component.literal("Accepted teleport request from " + requester.getName().getString()));
        } else {
            target.teleportTo(
                    dimension,
                    requester.getX(), requester.getY(), requester.getZ(),
                    EnumSet.noneOf(Relative.class),
                    target.getYRot(), target.getXRot(),
                    true
            );
            requester.sendSystemMessage(Component.literal(target.getName().getString() + " is teleporting to you."));
            target.sendSystemMessage(Component.literal("Teleporting to " + requester.getName().getString()));
        }

        TeleportUtils.clearRequest(targetId);
        return 0;
    }

    private int sendTpaHere(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer requester = context.getSource().getPlayerOrException();
        ServerPlayer target = EntityArgument.getPlayer(context, "target");

        if (isSelfRequest(requester, target))
            return 1;

        TeleportUtils.sendTpaHereRequest(requester, target);
        requester.sendSystemMessage(Component.literal("Teleport request sent to " + target.getName().getString()));
        return 0;
    }

    private int denyTpa(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer target = context.getSource().getPlayerOrException();
        UUID targetId = target.getUUID();

        UUID requesterId = TeleportUtils.getTpaRequester(targetId);
        UUID hereRequesterId = TeleportUtils.getTpaHereRequester(targetId);

        if (requesterId == null && hereRequesterId == null) {
            target.sendSystemMessage(Component.literal("No pending teleport requests."));
            return 1;
        }

        ServerPlayer requester = (requesterId != null)
                ? target.server.getPlayerList().getPlayer(requesterId)
                : target.server.getPlayerList().getPlayer(hereRequesterId);

        if (requester == null) {
            context.getSource().sendFailure(Component.literal("No requester found."));
            return -1;
        }

        requester.sendSystemMessage(Component.literal(target.getName().getString() + " denied your teleport request."));

        target.sendSystemMessage(Component.literal("Teleport request denied."));
        TeleportUtils.clearRequest(targetId);
        return 0;
    }

    private boolean isSelfRequest(ServerPlayer requester, ServerPlayer target) {
        if (requester.getUUID().equals(target.getUUID())) {
            requester.sendSystemMessage(Component.literal("You can't send a teleport request to yourself."));
            return true;
        }
        return false;
    }
}
