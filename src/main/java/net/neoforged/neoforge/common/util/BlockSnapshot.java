package net.neoforged.neoforge.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record BlockSnapshot(ResourceKey<Level> dimension, Level level, BlockPos pos) {
	public static BlockSnapshot create(ResourceKey<Level> dimension, Level level, BlockPos pos) {
		return new BlockSnapshot(dimension, level, pos);
	}
}
