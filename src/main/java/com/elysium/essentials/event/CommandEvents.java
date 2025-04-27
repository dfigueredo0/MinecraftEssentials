package com.elysium.essentials.event;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.commands.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = Essentials.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CommandEvents {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new HealCommand(event.getDispatcher());
        new GameModeCommands(event.getDispatcher());
        new FlyCommand(event.getDispatcher());
        new SpawnCommand(event.getDispatcher());
        new TPACommands(event.getDispatcher());
        new InvSeeCommand(event.getDispatcher());
        new EnderChestCommand(event.getDispatcher());
        new RepairCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getEntity().getPersistentData().putByte("flying",
                event.getOriginal().getPersistentData().getByte("flying"));
    }
}
