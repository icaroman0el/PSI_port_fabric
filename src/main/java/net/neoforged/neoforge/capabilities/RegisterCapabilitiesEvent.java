package net.neoforged.neoforge.capabilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;

public class RegisterCapabilitiesEvent {
	public <T, C, E extends net.minecraft.world.entity.Entity> void registerEntity(EntityCapability<T, C> capability, EntityType<E> entityType, BiFunction<E, C, T> provider) {
	}

	@SafeVarargs
	public final <T, C> void registerItem(ItemCapability<T, C> capability, BiFunction<net.minecraft.world.item.ItemStack, C, T> provider, Item... items) {
	}
}
