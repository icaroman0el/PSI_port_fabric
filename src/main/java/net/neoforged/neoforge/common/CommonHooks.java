package net.neoforged.neoforge.common;

import net.minecraft.world.item.ItemStack;

public final class CommonHooks {
	private CommonHooks() {}

	public static ItemStack getCraftingRemainingItem(ItemStack stack) {
		if(stack.isEmpty() || !stack.getItem().hasCraftingRemainingItem()) {
			return ItemStack.EMPTY;
		}
		return new ItemStack(stack.getItem().getCraftingRemainingItem());
	}
}
