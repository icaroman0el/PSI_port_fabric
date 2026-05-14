package net.neoforged.neoforge.registries;

import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class DeferredRegister<T> {
	private final Registry<T> registry;
	private final ResourceKey<? extends Registry<T>> registryKey;
	private final String modid;
	private final List<DeferredHolder<T, ? extends T>> entries = new ArrayList<>();

	private DeferredRegister(Registry<T> registry, ResourceKey<? extends Registry<T>> registryKey, String modid) {
		this.registry = Objects.requireNonNull(registry);
		this.registryKey = Objects.requireNonNull(registryKey);
		this.modid = modid;
	}

	public static <T> DeferredRegister<T> create(Registry<T> registry, String modid) {
		return new DeferredRegister<>(registry, registry.key(), modid);
	}

	@SuppressWarnings("unchecked")
	public static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> registryKey, String modid) {
		Registry<T> registry = (Registry<T>) RegistryBuilder.getOrCreate(registryKey);
		return new DeferredRegister<>(registry, registryKey, modid);
	}

	public static DataComponents createDataComponents(ResourceKey<? extends Registry<DataComponentType<?>>> registryKey, String modid) {
		return new DataComponents((Registry<DataComponentType<?>>) RegistryBuilder.getOrCreate(registryKey), registryKey, modid);
	}

	public <I extends T> DeferredHolder<T, I> register(String name, Supplier<? extends I> supplier) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modid, name);
		I value = Registry.register(registry, id, supplier.get());
		DeferredHolder<T, I> holder = new DeferredHolder<>(registryKey, id, value);
		entries.add(holder);
		return holder;
	}

	public void register() {
	}

	public void register(Object ignored) {
	}

	public Collection<DeferredHolder<T, ? extends T>> getEntries() {
		return List.copyOf(entries);
	}

	public static class DataComponents extends DeferredRegister<DataComponentType<?>> {
		private DataComponents(Registry<DataComponentType<?>> registry, ResourceKey<? extends Registry<DataComponentType<?>>> registryKey, String modid) {
			super(registry, registryKey, modid);
		}

		public <D> DeferredHolder<DataComponentType<?>, DataComponentType<D>> registerComponentType(String name, UnaryOperator<DataComponentType.Builder<D>> builder) {
			return register(name, () -> builder.apply(DataComponentType.builder()).build());
		}
	}
}
