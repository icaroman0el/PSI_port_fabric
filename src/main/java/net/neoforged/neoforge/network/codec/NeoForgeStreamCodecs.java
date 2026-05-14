package net.neoforged.neoforge.network.codec;

import net.minecraft.network.codec.StreamCodec;

import java.util.function.Supplier;

public final class NeoForgeStreamCodecs {
	private NeoForgeStreamCodecs() {}

	public static <B, V> StreamCodec<B, V> lazy(Supplier<StreamCodec<B, V>> supplier) {
		return new StreamCodec<>() {
			@Override
			public V decode(B buffer) {
				return supplier.get().decode(buffer);
			}

			@Override
			public void encode(B buffer, V value) {
				supplier.get().encode(buffer, value);
			}
		};
	}
}
