package net.neoforged.neoforge.network.handling;

import net.minecraft.world.entity.player.Player;

public interface IPayloadContext {
	Player player();

	default void enqueueWork(Runnable runnable) {
		runnable.run();
	}
}
