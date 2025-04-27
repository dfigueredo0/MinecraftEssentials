package com.elysium.essentials.commands;

import com.elysium.essentials.data.tags.ModTags;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;

public class RepairCommand {
    public RepairCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("repair")
                .executes(context -> {
                    if (context.getSource().hasPermission(2))
                        return executeAdmin(context);
                    else
                        return executePlayer(context);
                    }
                ));
    }

    private static int executeAdmin(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
        ItemStack tool = serverPlayer.getMainHandItem();

        if (!(tool.is(ModTags.Items.REPAIRABLE))) {
            context.getSource().sendFailure(Component.literal(tool.getHoverName().getString() + " is not a repairable item!"));
            return -1;
        }

        if (!tool.isDamaged()) {
            context.getSource().sendFailure(Component.literal(tool.getHoverName().getString() + " does not need any repairs!"));
            return -1;
        }

        tool.setDamageValue(0);

        return 0;
    }

    private static int executePlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
        ItemStack tool = serverPlayer.getMainHandItem();

        if (!(tool.is(ModTags.Items.REPAIRABLE))) {
            context.getSource().sendFailure(Component.literal(tool.getHoverName().getString() + " is not a repairable item!"));
            return -1;
        }

        if (!tool.isDamaged()) {
            context.getSource().sendFailure(Component.literal(tool.getHoverName().getString() + " does not need any repairs!"));
            return -1;
        }

        serverPlayer.openMenu(new SimpleMenuProvider(
                (id, inventory, player) -> new AnvilMenu(id, inventory),
                Component.literal("Repair Screen")
        ));

        return 0;
    }
}
