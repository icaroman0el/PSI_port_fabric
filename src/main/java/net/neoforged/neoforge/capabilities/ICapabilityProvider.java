package net.neoforged.neoforge.capabilities;

public interface ICapabilityProvider<CAP, CTX, T> {
	T getCapability(CAP capability, CTX context);
}
