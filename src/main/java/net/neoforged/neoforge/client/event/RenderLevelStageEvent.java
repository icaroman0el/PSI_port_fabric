package net.neoforged.neoforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.DeltaTracker;

public class RenderLevelStageEvent {
	public enum Stage {
		AFTER_PARTICLES
	}

	private final Stage stage;
	private final DeltaTracker partialTick;
	private final PoseStack poseStack;

	public RenderLevelStageEvent(Stage stage, DeltaTracker partialTick, PoseStack poseStack) {
		this.stage = stage;
		this.partialTick = partialTick;
		this.poseStack = poseStack;
	}

	public Stage getStage() {
		return stage;
	}

	public DeltaTracker getPartialTick() {
		return partialTick;
	}

	public PoseStack getPoseStack() {
		return poseStack;
	}
}
