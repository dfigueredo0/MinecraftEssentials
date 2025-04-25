package com.elysium.essentials.event.level;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.data.tags.ModTags;
import com.elysium.essentials.network.status.HarvestAllStatus;
import com.elysium.essentials.util.BlockBreakUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Essentials.MODID, bus = EventBusSubscriber.Bus.GAME)
public class BlockEvents {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer player))
            return;

        Level level = player.level();
        BlockPos startPos = event.getPos();
        BlockState startState = event.getState();

        if (!((player.getMainHandItem().getItem() instanceof PickaxeItem) || (player.getMainHandItem().getItem() instanceof AxeItem)))
            return;

        if (!(startState.is(ModTags.Blocks.MINEABLE_ORES) || startState.is(BlockTags.LOGS)))
            return;

        if (!HarvestAllStatus.isEnabled(player))
            return;

        BlockBreakUtils.mineConnectedBlocks(level, player, startPos, startState.getBlock());
    }
}
