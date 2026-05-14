package net.neoforged.neoforge.event;

import net.minecraft.world.item.ItemStack;

public class ItemAttributeModifierEvent {
	private final ItemStack itemStack;

	public ItemAttributeModifierEvent(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void removeAllModifiersFor(Object attribute) {
	}
}
