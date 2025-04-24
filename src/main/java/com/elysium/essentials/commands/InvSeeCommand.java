package com.elysium.essentials.commands;

import com.elysium.essentials.world.inventory.InvSeeMenu;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;

public class InvSeeCommand {
    public InvSeeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("invsee")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(this::execute))
        );
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer viewer;
        ServerPlayer target;

        try {
            viewer = context.getSource().getPlayerOrException();
            target = EntityArgument.getPlayer(context, "target");
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("Could not find player."));
            return -1;
        }

        if (viewer.getUUID().equals(target.getUUID())) {
            viewer.sendSystemMessage(Component.literal("You can't view your own inventory."));
            return 1;
        }

        viewer.openMenu(new SimpleMenuProvider(
                (id, inventory, player) -> new InvSeeMenu(id, inventory, target),
                Component.literal("Inventory of " + target.getName().getString())
        ));

        return 0;
    }
}
