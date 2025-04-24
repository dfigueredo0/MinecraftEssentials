package com.elysium.essentials.util;

import net.minecraft.core.BlockPos;

public class MathUtils {
    public static int manhattanDistance(BlockPos a, BlockPos b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) + Math.abs(a.getZ() - b.getZ());
    }
}
