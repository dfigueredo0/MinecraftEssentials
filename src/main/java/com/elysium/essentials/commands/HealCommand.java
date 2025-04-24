package com.elysium.essentials.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class HealCommand {
    public HealCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("heal")
                        .requires(source -> source.hasPermission(2))
                        .executes(this::executeSelf)
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(this::executeOther))
        );
    }

    private int executeSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        return healPlayer(context, player);
    }

    private int executeOther(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer target = EntityArgument.getPlayer(context, "target");
        return healPlayer(context, target);
    }

    private int healPlayer(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        if (player.isCreative() || player.isSpectator()) {
            context.getSource().sendFailure(Component.literal("Invalid Gamemode!"));
            return -1;
        }

        player.setHealth(player.getMaxHealth());
        player.getFoodData().setFoodLevel(20);
        player.clearFire();
        player.removeAllEffects();

        context.getSource().sendSuccess(() -> Component.literal("Healed " + player.getDisplayName().getString()), false);
        return 0;
    }
}
