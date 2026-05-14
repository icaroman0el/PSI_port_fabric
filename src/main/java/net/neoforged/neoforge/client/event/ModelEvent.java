package net.neoforged.neoforge.client.event;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;

import java.util.Map;

public class ModelEvent {
	public static class ModifyBakingResult {
		private final Map<ModelResourceLocation, BakedModel> models;

		public ModifyBakingResult(Map<ModelResourceLocation, BakedModel> models) {
			this.models = models;
		}

		public Map<ModelResourceLocation, BakedModel> getModels() {
			return models;
		}
	}

	public static class RegisterAdditional {
		public void register(ModelResourceLocation location) {
		}
	}
}
