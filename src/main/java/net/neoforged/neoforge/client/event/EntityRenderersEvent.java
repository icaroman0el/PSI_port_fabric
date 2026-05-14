package net.neoforged.neoforge.client.event;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class EntityRenderersEvent {
	public static class RegisterRenderers {
		public <T extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<T> type, BlockEntityRendererProvider<? super T> provider) {
		}

		public <T extends Entity> void registerEntityRenderer(EntityType<? extends T> type, EntityRendererProvider<T> provider) {
		}
	}

	public static class RegisterLayerDefinitions {
		public void registerLayerDefinition(ModelLayerLocation layer, Supplier<LayerDefinition> supplier) {
		}
	}
}
