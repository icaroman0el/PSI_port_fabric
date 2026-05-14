package net.neoforged.neoforge.items;

import net.minecraft.world.item.ItemStack;

public final class ItemHandlerHelper {
	private ItemHandlerHelper() {}

	public static int calcRedstoneFromInventory(IItemHandler handler) {
		int slots = handler.getSlots();
		if(slots == 0) {
			return 0;
		}

		float fullness = 0.0F;
		int occupied = 0;
		for(int slot = 0; slot < slots; slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			if(!stack.isEmpty()) {
				fullness += (float) stack.getCount() / Math.min(handler.getSlotLimit(slot), stack.getMaxStackSize());
				occupied++;
			}
		}

		fullness /= slots;
		return (int) Math.floor(fullness * 14.0F) + (occupied > 0 ? 1 : 0);
	}
}
