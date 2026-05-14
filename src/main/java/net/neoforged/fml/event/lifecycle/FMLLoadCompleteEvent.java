package net.neoforged.fml.event.lifecycle;

public class FMLLoadCompleteEvent {
	public void enqueueWork(Runnable runnable) {
		runnable.run();
	}
}
