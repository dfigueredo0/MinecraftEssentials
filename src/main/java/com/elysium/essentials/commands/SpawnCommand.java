package com.elysium.essentials.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;

import java.util.EnumSet;


public class SpawnCommand {
    private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(
            Component.translatable("commands.teleport.invalidPosition")
    );

    public SpawnCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("spawn")
                        .requires(source -> source.hasPermission(0))
                        .executes(this::execute)
        );
    }

    private int teleportToSpawn(CommandSourceStack source, Entity target, ServerLevel level, BlockPos pos) throws CommandSyntaxException {
        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;
        float yRot = target.getYRot();
        float xRot = target.getXRot();

        // NeoForge event handling
        EntityTeleportEvent.TeleportCommand event = net.neoforged.neoforge.event.EventHooks.onEntityTeleportCommand(target, x, y, z);
        if (event.isCanceled()) return 1;

        x = event.getTargetX();
        y = event.getTargetY();
        z = event.getTargetZ();

        BlockPos newPos = BlockPos.containing(x, y, z);
        if (!Level.isInSpawnableBounds(newPos)) throw INVALID_POSITION.create();

        if (target.teleportTo(level, x, y, z, EnumSet.noneOf(Relative.class), yRot, xRot, true)) {
            if (!(target instanceof LivingEntity le) || !le.isFallFlying()) {
                target.setDeltaMovement(target.getDeltaMovement().multiply(1.0, 0.0, 1.0));
                target.setOnGround(true);
            }
            if (target instanceof PathfinderMob mob) {
                mob.getNavigation().stop();
            }
        }

        source.sendSuccess(() -> Component.literal("Teleported to spawn!"), false);
        return 0;
    }

    private int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        ServerLevel level = source.getLevel();
        BlockPos spawnPos = level.getSharedSpawnPos();
        return teleportToSpawn(source, player, level, spawnPos);
    }
}