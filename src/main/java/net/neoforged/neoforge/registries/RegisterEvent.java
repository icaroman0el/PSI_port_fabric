package net.neoforged.neoforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Consumer;

public class RegisterEvent {
	public <T> void register(ResourceKey<? extends Registry<T>> registryKey, Consumer<Helper<T>> consumer) {
	}

	public interface Helper<T> {
		void register(net.minecraft.resources.ResourceLocation id, T value);

		default void register(ResourceKey<T> id, T value) {
			register(id.location(), value);
		}
	}
}
