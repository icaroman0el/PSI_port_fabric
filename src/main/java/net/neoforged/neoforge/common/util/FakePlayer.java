package net.neoforged.neoforge.common.util;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public abstract class FakePlayer extends Player {
	protected FakePlayer(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
		super(level, pos, yRot, gameProfile);
	}

	protected FakePlayer(Level level) {
		this(level, BlockPos.ZERO, 0, new GameProfile(new UUID(0, 0), "FakePlayer"));
	}
}
