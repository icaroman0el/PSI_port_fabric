package net.neoforged.neoforge.network.registration;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.BiConsumer;

public class PayloadRegistrar {
	public PayloadRegistrar versioned(String version) {
		return this;
	}

	public PayloadRegistrar optional() {
		return this;
	}

	public <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, BiConsumer<T, IPayloadContext> handler) {
	}
}
