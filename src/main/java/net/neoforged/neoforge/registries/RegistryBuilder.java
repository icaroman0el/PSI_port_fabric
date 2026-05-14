package net.neoforged.neoforge.registries;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.Map;

public class RegistryBuilder<T> {
	private static final Map<ResourceKey<? extends Registry<?>>, Registry<?>> REGISTRIES = new HashMap<>();

	private final ResourceKey<Registry<T>> key;

	public RegistryBuilder(ResourceKey<Registry<T>> key) {
		this.key = key;
	}

	@SuppressWarnings("unchecked")
	static <T> Registry<T> getOrCreate(ResourceKey<? extends Registry<T>> key) {
		Registry<T> builtin = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location());
		if(builtin != null) {
			return builtin;
		}

		return (Registry<T>) REGISTRIES.computeIfAbsent((ResourceKey<? extends Registry<?>>) key, registryKey -> FabricRegistryBuilder.createSimple((ResourceKey) registryKey).buildAndRegister());
	}

	public Registry<T> create() {
		Registry<T> registry = getOrCreate(key);
		REGISTRIES.put(key, registry);
		return registry;
	}
}
