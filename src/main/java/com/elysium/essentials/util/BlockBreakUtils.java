package com.elysium.essentials.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

import static com.elysium.essentials.util.MathUtils.manhattanDistance;

public class BlockBreakUtils {
    private static class BlockNode implements Comparable<BlockNode> {
        BlockPos pos;
        int priority;

        public BlockNode(BlockPos pos, int priority) {
            this.pos = pos;
            this.priority = priority;
        }

        @Override
        public int compareTo(BlockNode other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    public static void mineConnectedBlocks(Level level, ServerPlayer player, BlockPos origin, Block targetBlock) {
        Set<BlockPos> visited = new HashSet<>();
        PriorityQueue<BlockNode> frontier = new PriorityQueue<>();
        frontier.add(new BlockNode(origin, 0));

        ItemStack tool = player.getMainHandItem();

        while (!frontier.isEmpty()) {
            BlockNode currentNode = frontier.poll();
            BlockPos currentPos = currentNode.pos;

            if (!visited.add(currentPos)) continue;

            BlockState currentState = level.getBlockState(currentPos);
            if (currentState.getBlock() != targetBlock) continue;

            level.destroyBlock(currentPos, true, player);

            for (BlockPos offset : BlockPos.betweenClosed(-1, -1, -1, 1, 1, 1)) {
                BlockPos neighbor = currentPos.offset(offset.getX(), offset.getY(), offset.getZ());

                if (!visited.contains(neighbor)) {
                    int priority = manhattanDistance(origin, neighbor);
                    frontier.add(new BlockNode(neighbor, priority));
                }
            }
        }
    }
}
