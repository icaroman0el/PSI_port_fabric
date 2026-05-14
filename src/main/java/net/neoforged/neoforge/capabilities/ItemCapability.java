package net.neoforged.neoforge.capabilities;

import net.minecraft.resources.ResourceLocation;

public final class ItemCapability<T, C> {
	private final ResourceLocation id;
	private final Class<T> type;

	private ItemCapability(ResourceLocation id, Class<T> type) {
		this.id = id;
		this.type = type;
	}

	public static <T> ItemCapability<T, Void> createVoid(ResourceLocation id, Class<T> type) {
		return new ItemCapability<>(id, type);
	}

	public ResourceLocation id() {
		return id;
	}

	public Class<T> type() {
		return type;
	}
}
