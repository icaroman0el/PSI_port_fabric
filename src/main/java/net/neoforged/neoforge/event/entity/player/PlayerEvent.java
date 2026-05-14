package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public class PlayerEvent extends Event {
	private final Player entity;

	public PlayerEvent(Player entity) {
		this.entity = entity;
	}

	public Player getEntity() {
		return entity;
	}

	public static class PlayerLoggedInEvent extends PlayerEvent {
		public PlayerLoggedInEvent(Player entity) {
			super(entity);
		}
	}

	public static class PlayerChangedDimensionEvent extends PlayerEvent {
		public PlayerChangedDimensionEvent(Player entity) {
			super(entity);
		}
	}

	public static class PlayerRespawnEvent extends PlayerEvent {
		public PlayerRespawnEvent(Player entity) {
			super(entity);
		}
	}

	public static class StartTracking extends PlayerEvent {
		private final Entity target;

		public StartTracking(Player entity, Entity target) {
			super(entity);
			this.target = target;
		}

		public Entity getTarget() {
			return target;
		}
	}

	public static class ItemCraftedEvent extends PlayerEvent {
		private final ItemStack crafting;

		public ItemCraftedEvent(Player entity) {
			this(entity, ItemStack.EMPTY);
		}

		public ItemCraftedEvent(Player entity, ItemStack crafting) {
			super(entity);
			this.crafting = crafting;
		}

		public ItemStack getCrafting() {
			return crafting;
		}
	}
}
