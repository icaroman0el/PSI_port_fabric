package net.neoforged.neoforge.common;

import net.neoforged.bus.api.IEventBus;

public final class NeoForge {
	public static final EventBus EVENT_BUS = new EventBus();

	private NeoForge() {}

	public static class EventBus implements IEventBus {
		public <T> T post(T event) {
			return event;
		}
	}
}
