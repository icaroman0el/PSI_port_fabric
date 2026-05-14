package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;

public class PlayerInteractEvent extends PlayerEvent implements ICancellableEvent {
	private InteractionResult cancellationResult = InteractionResult.PASS;

	public PlayerInteractEvent(Player entity) {
		super(entity);
	}

	public void setCancellationResult(InteractionResult result) {
		this.cancellationResult = result;
	}

	public InteractionResult getCancellationResult() {
		return cancellationResult;
	}

	public static class EntityInteractSpecific extends PlayerInteractEvent {
		private final Entity target;
		private final InteractionHand hand;

		public EntityInteractSpecific(Player entity, Entity target, InteractionHand hand) {
			super(entity);
			this.target = target;
			this.hand = hand;
		}

		public Entity getTarget() {
			return target;
		}

		public InteractionHand getHand() {
			return hand;
		}
	}
}
