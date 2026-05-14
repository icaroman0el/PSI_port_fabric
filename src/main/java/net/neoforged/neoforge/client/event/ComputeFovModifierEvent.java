package net.neoforged.neoforge.client.event;

public class ComputeFovModifierEvent {
	private float newFovModifier;

	public ComputeFovModifierEvent(float newFovModifier) {
		this.newFovModifier = newFovModifier;
	}

	public float getNewFovModifier() {
		return newFovModifier;
	}

	public void setNewFovModifier(float newFovModifier) {
		this.newFovModifier = newFovModifier;
	}
}
