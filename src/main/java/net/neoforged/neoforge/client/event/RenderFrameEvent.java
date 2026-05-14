package net.neoforged.neoforge.client.event;

import net.minecraft.client.DeltaTracker;

public class RenderFrameEvent {
	public DeltaTracker getPartialTick() {
		return DeltaTracker.ZERO;
	}

	public static class Pre extends RenderFrameEvent {}
	public static class Post extends RenderFrameEvent {}
}
