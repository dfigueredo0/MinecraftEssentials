package com.elysium.essentials.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForgeMod;

public class FlyCommand {
    public FlyCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("fly")
                        .requires(source -> source.hasPermission(2))
                        .executes(this::executeSelf)
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(this::executeOther))
        );
    }

    private int executeSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer self = context.getSource().getPlayerOrException();
        return toggleFlight(context, self);
    }

    private int executeOther(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer target = EntityArgument.getPlayer(context, "target");
        return toggleFlight(context, target);
    }

    private int toggleFlight(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        byte current = player.getPersistentData().getByte("flying");
        boolean enable = current == 0;

        if (player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT) != null) {
            player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).setBaseValue(enable ? 1.0 : 0.0);
        }

        player.getPersistentData().putByte("flying", (byte)(enable ? 1 : 0));

        String result = (enable ? "Enabled" : "Disabled") + " flight for " + player.getDisplayName().getString();
        context.getSource().sendSuccess(() -> Component.literal(result), false);
        return 0;
    }
}
