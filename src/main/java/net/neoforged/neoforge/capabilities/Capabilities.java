package net.neoforged.neoforge.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.items.IItemHandler;

public final class Capabilities {
	private Capabilities() {}

	public static final class ItemHandler {
		public static final ItemCapability<IItemHandler, Void> ITEM =
				ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath("neoforge", "item_handler"), IItemHandler.class);
		public static final ItemCapability<IItemHandler, Void> BLOCK = ITEM;

		private ItemHandler() {}
	}
}
