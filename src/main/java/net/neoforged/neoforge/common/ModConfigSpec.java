package net.neoforged.neoforge.common;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

public class ModConfigSpec {
	public static class Builder {
		public Builder comment(String comment) {
			return this;
		}

		public BooleanValue define(String path, boolean defaultValue) {
			return new BooleanValue(defaultValue);
		}

		public IntValue defineInRange(String path, int defaultValue, int min, int max) {
			return new IntValue(defaultValue);
		}

		public <T> Pair<T, ModConfigSpec> configure(Function<Builder, T> factory) {
			return Pair.of(factory.apply(this), new ModConfigSpec());
		}
	}

	public static class BooleanValue {
		private final boolean value;

		public BooleanValue(boolean value) {
			this.value = value;
		}

		public boolean get() {
			return value;
		}
	}

	public static class IntValue {
		private final int value;

		public IntValue(int value) {
			this.value = value;
		}

		public int get() {
			return value;
		}
	}
}
