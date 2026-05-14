package net.neoforged.neoforge.event.tick;

import net.minecraft.world.level.Level;

public class LevelTickEvent {
	private final Level level;

	public LevelTickEvent(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

	public static class Post extends LevelTickEvent {
		public Post(Level level) {
			super(level);
		}
	}
}
