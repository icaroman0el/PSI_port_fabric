package net.neoforged.bus.api;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public interface ICancellableEvent {
	Map<ICancellableEvent, Boolean> CANCELED = Collections.synchronizedMap(new WeakHashMap<>());

	default boolean isCanceled() {
		return CANCELED.getOrDefault(this, false);
	}

	default void setCanceled(boolean canceled) {
		if(canceled) {
			CANCELED.put(this, true);
		} else {
			CANCELED.remove(this);
		}
	}
}
