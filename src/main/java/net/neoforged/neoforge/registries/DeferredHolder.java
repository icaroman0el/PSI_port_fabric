package net.neoforged.neoforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public final class DeferredHolder<R, T extends R> implements Supplier<T> {
	private final ResourceKey<? extends Registry<R>> registryKey;
	private final ResourceLocation id;
	private final T value;

	DeferredHolder(ResourceKey<? extends Registry<R>> registryKey, ResourceLocation id, T value) {
		this.registryKey = registryKey;
		this.id = id;
		this.value = value;
	}

	@Override
	public T get() {
		return value;
	}

	public ResourceLocation getId() {
		return id;
	}

	public ResourceKey<R> getKey() {
		return ResourceKey.create(registryKey, id);
	}
}
