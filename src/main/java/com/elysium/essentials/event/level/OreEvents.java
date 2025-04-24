package com.elysium.essentials.event.level;

import com.elysium.essentials.Essentials;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Essentials.MODID, bus = EventBusSubscriber.Bus.GAME)
public class OreEvents {
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player))
            return;

        Level level = player.level();
        BlockPos startPos = event.getPos();
        BlockState startState = event.getState();

        if (!(player.getMainHandItem().getItem() instanceof PickaxeItem))
            return;


    }
}
