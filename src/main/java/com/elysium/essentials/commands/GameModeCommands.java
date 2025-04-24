package com.elysium.essentials.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class GameModeCommands {
    public GameModeCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("gmc")
                        .requires(source -> source.hasPermission(2))
                        .executes(this::setCreative)
        );

        dispatcher.register(
                Commands.literal("gms")
                        .requires(source -> source.hasPermission(2))
                        .executes(this::setSurvival)
        );
    }

    private int setCreative(CommandContext<CommandSourceStack> context) {
        return setGamemode(context, GameType.CREATIVE);
    }

    private int setSurvival(CommandContext<CommandSourceStack> context) {
        return setGamemode(context, GameType.SURVIVAL);
    }

    private int setGamemode(CommandContext<CommandSourceStack> context, GameType gameType) {
        CommandSourceStack source = context.getSource();
        if (source.getEntity() instanceof ServerPlayer player) {
            player.setGameMode(gameType);
            source.sendSuccess(() -> Component.literal("Set gamemode to " + gameType.getName()), true);
            return 1;
        } else {
            source.sendFailure(Component.literal("Only players can use this command."));
            return 0;
        }
    }
}