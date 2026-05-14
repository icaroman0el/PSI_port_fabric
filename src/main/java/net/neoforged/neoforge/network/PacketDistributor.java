package net.neoforged.neoforge.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public final class PacketDistributor {
	private PacketDistributor() {}

	public static <MSG extends CustomPacketPayload> void sendToServer(MSG message) {
	}

	public static <MSG extends CustomPacketPayload> void sendToPlayer(ServerPlayer player, MSG message) {
	}

	public static <MSG extends CustomPacketPayload> void sendToPlayersTrackingEntity(Entity entity, MSG message) {
	}

	public static <MSG extends CustomPacketPayload> void sendToPlayersTrackingEntityAndSelf(Entity entity, MSG message) {
	}

	public static <MSG extends CustomPacketPayload> void sendToPlayersInDimension(ServerLevel level, MSG message) {
	}
}
