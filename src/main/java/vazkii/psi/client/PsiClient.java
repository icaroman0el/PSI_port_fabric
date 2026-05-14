package vazkii.psi.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import vazkii.psi.client.core.proxy.ClientProxy;
import vazkii.psi.client.gui.GuiCADAssembler;
import vazkii.psi.client.model.ArmorModels;
import vazkii.psi.client.model.ModModelLayers;
import vazkii.psi.client.model.ModelArmor;
import vazkii.psi.client.model.ModelCAD;
import vazkii.psi.client.model.ModelPsimetalExosuit;
import vazkii.psi.client.render.spell.SpellPieceMaterial;
import vazkii.psi.common.Psi;
import vazkii.psi.common.block.base.ModBlocks;
import vazkii.psi.common.item.base.ModItems;
import vazkii.psi.common.lib.LibResources;

public class PsiClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Psi.proxy = new ClientProxy();
		SpellPieceMaterial.SPELL_PIECE_MATERIAL.register();
		MenuScreens.register(ModBlocks.containerCADAssembler.get(), GuiCADAssembler::new);
		registerArmorRendering();
		registerCADModels();
	}

	private static void registerCADModels() {
		ModelLoadingPlugin.register(context -> {
			context.addModels(ModelCAD.CAD_MODELS);
			context.modifyModelAfterBake().register((model, modelContext) -> {
				if(model == null) {
					return null;
				}

				if(Psi.location("item/cad").equals(modelContext.resourceId())
						|| ModelResourceLocation.inventory(Psi.location("cad")).equals(modelContext.topLevelId())) {
					return new ModelCAD(model);
				}

				return model;
			});
		});
	}

	private static void registerArmorRendering() {
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PSIMETAL_EXOSUIT_INNER_ARMOR, () -> LayerDefinition.create(ModelPsimetalExosuit.createInsideMesh(), 64, 128));
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PSIMETAL_EXOSUIT_OUTER_ARMOR, () -> LayerDefinition.create(ModelPsimetalExosuit.createOutsideMesh(), 64, 128));

		ArmorRenderer.register(PsiClient::renderPsimetalArmor,
				ModItems.psimetalExosuitHelmet.get(),
				ModItems.psimetalExosuitChestplate.get(),
				ModItems.psimetalExosuitLeggings.get(),
				ModItems.psimetalExosuitBoots.get());
	}

	private static void renderPsimetalArmor(com.mojang.blaze3d.vertex.PoseStack matrices, net.minecraft.client.renderer.MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
		ModelArmor model = ArmorModels.get(stack);
		if(model == null) {
			ArmorModels.init(Minecraft.getInstance().getEntityModels());
			model = ArmorModels.get(stack);
		}
		if(model == null) {
			return;
		}

		contextModel.copyPropertiesTo(model);
		setVisibleParts(model, slot);
		ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, LibResources.MODEL_PSIMETAL_EXOSUIT);
	}

	private static void setVisibleParts(ModelArmor model, EquipmentSlot slot) {
		model.setAllVisible(false);
		switch(slot) {
		case HEAD -> {
			model.head.visible = true;
			model.hat.visible = true;
		}
		case CHEST -> {
			model.body.visible = true;
			model.rightArm.visible = true;
			model.leftArm.visible = true;
		}
		case LEGS -> {
			model.body.visible = true;
			model.rightLeg.visible = true;
			model.leftLeg.visible = true;
		}
		case FEET -> {
			model.rightLeg.visible = true;
			model.leftLeg.visible = true;
		}
		default -> {
		}
		}
	}
}
