package net.neoforged.neoforge.client.event;

public class InputEvent {
	public static class Key extends InputEvent {
		public int getModifiers() {
			return 0;
		}

		public int getAction() {
			return 0;
		}

		public int getKey() {
			return 0;
		}
	}
}
