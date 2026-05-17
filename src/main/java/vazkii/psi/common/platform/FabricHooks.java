/*
 * This class is distributed as part of the Psi Mod.
 * Get the Source Code in GitHub:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: https://psi.vazkii.net/license.php
 */
package vazkii.psi.common.platform;

import net.minecraft.world.item.ItemStack;

public final class FabricHooks {
	private FabricHooks() {}

	public static ItemStack getCraftingRemainingItem(ItemStack stack) {
		if(stack.isEmpty() || !stack.getItem().hasCraftingRemainingItem()) {
			return ItemStack.EMPTY;
		}
		return new ItemStack(stack.getItem().getCraftingRemainingItem());
	}
}
