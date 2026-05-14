package net.neoforged.neoforge.client.event;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;

public class RegisterColorHandlersEvent {
	public static class Item {
		public void register(ItemColor color, net.minecraft.world.item.Item... items) {
		}
	}
}
