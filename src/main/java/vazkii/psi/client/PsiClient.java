package vazkii.psi.client;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import vazkii.psi.api.spell.ISpellAcceptor;
import vazkii.psi.api.spell.SpellPiece;
import vazkii.psi.client.core.handler.BookSoundHandler;
import vazkii.psi.client.core.handler.ClientTickHandler;
import vazkii.psi.client.core.handler.HUDHandler;
import vazkii.psi.client.core.handler.KeybindHandler;
import vazkii.psi.client.core.handler.ShaderHandler;
import vazkii.psi.client.core.proxy.ClientProxy;
import vazkii.psi.client.fx.FXSparkle;
import vazkii.psi.client.fx.FXWisp;
import vazkii.psi.client.fx.ModParticles;
import vazkii.psi.client.gui.GuiCADAssembler;
import vazkii.psi.client.gui.GuiProgrammer;
import vazkii.psi.client.model.ArmorModels;
import vazkii.psi.client.model.ModModelLayers;
import vazkii.psi.client.model.ModelArmor;
import vazkii.psi.client.model.ModelCAD;
import vazkii.psi.client.model.ModelPsimetalExosuit;
import vazkii.psi.client.network.ClientNetworkHelper;
import vazkii.psi.client.render.entity.RenderSpellCircle;
import vazkii.psi.client.render.entity.RenderSpellProjectile;
import vazkii.psi.client.render.spell.SpellPieceMaterial;
import vazkii.psi.client.render.tile.RenderTileProgrammer;
import vazkii.psi.common.Psi;
import vazkii.psi.common.block.base.ModBlocks;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import vazkii.psi.common.entity.ModEntities;
import vazkii.psi.common.item.ItemCAD;
import vazkii.psi.common.item.ItemExosuitSensor;
import vazkii.psi.common.item.armor.ItemPsimetalArmor;
import vazkii.psi.common.item.base.ModItems;
import vazkii.psi.common.item.component.ItemCADColorizer;
import vazkii.psi.common.lib.LibResources;
import vazkii.psi.mixin.client.AccessorParticleEngine;
import vazkii.psi.mixin.client.AccessorRenderBuffers;

import java.util.ArrayList;
import java.util.List;
import java.util.SequencedMap;

public class PsiClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Psi.proxy = new ClientProxy();
		SpellPieceMaterial.SPELL_PIECE_MATERIAL.register();
		MenuScreens.register(ModBlocks.containerCADAssembler.get(), GuiCADAssembler::new);
		registerEntityRendering();
		registerParticleRenderTypes();
		registerParticleProviders();
		registerArmorRendering();
		registerCADModels();
		registerItemProperties();
		registerColorProviders();
		registerClientEvents();
		ShaderHandler.registerFabricShaders();
		ClientNetworkHelper.registerReceivers();
	}

	private static void registerClientEvents() {
		KeyBindingHelper.registerKeyBinding(KeybindHandler.keybind);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			ClientTickHandler.tickClient(client);
			BookSoundHandler.tickFabric(client);
		});
		HudRenderCallback.EVENT.register(HUDHandler::renderFabricHud);
		WorldRenderEvents.AFTER_ENTITIES.register(PlayerDataHandler::renderFabricWorld);
		ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
			SequencedMap<RenderType, ByteBufferBuilder> map = ((AccessorRenderBuffers) client.renderBuffers().bufferSource()).getFixedBuffers();
			RenderType layer = SpellPiece.getLayer();
			map.put(layer, new ByteBufferBuilder(layer.bufferSize()));
			map.put(GuiProgrammer.LAYER, new ByteBufferBuilder(GuiProgrammer.LAYER.bufferSize()));
		});
	}

	private static void registerEntityRendering() {
		BlockEntityRendererRegistry.register(ModBlocks.programmerType.get(), RenderTileProgrammer::new);
		EntityRendererRegistry.register(ModEntities.spellCircle, RenderSpellCircle::new);
		EntityRendererRegistry.register(ModEntities.spellCharge, RenderSpellProjectile::new);
		EntityRendererRegistry.register(ModEntities.spellGrenade, RenderSpellProjectile::new);
		EntityRendererRegistry.register(ModEntities.spellProjectile, RenderSpellProjectile::new);
		EntityRendererRegistry.register(ModEntities.spellMine, RenderSpellProjectile::new);
	}

	private static void registerParticleProviders() {
		ParticleFactoryRegistry.getInstance().register(ModParticles.WISP.get(), FXWisp.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.SPARKLE.get(), FXSparkle.Factory::new);
	}

	private static void registerParticleRenderTypes() {
		List<net.minecraft.client.particle.ParticleRenderType> current = AccessorParticleEngine.psi$getRenderOrder();
		if(current.contains(FXWisp.NORMAL_RENDER) && current.contains(FXSparkle.NORMAL_RENDER)) {
			return;
		}

		List<net.minecraft.client.particle.ParticleRenderType> renderOrder = new ArrayList<>(current);
		int customIndex = renderOrder.indexOf(net.minecraft.client.particle.ParticleRenderType.CUSTOM);
		int insertAt = customIndex >= 0 ? customIndex : renderOrder.size();
		renderOrder.add(insertAt, FXSparkle.NORMAL_RENDER);
		renderOrder.add(insertAt, FXWisp.NORMAL_RENDER);
		AccessorParticleEngine.psi$setRenderOrder(List.copyOf(renderOrder));
	}

	private static void registerColorProviders() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? ((ItemPsimetalArmor) stack.getItem()).getColor(stack) : 0xFFFFFFFF,
				ModItems.psimetalExosuitBoots.get(),
				ModItems.psimetalExosuitChestplate.get(),
				ModItems.psimetalExosuitHelmet.get(),
				ModItems.psimetalExosuitLeggings.get());

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? ((ItemExosuitSensor) stack.getItem()).getColor(stack) : 0xFFFFFFFF,
				ModItems.exosuitSensorHeat.get(),
				ModItems.exosuitSensorLight.get(),
				ModItems.exosuitSensorStress.get(),
				ModItems.exosuitSensorWater.get(),
				ModItems.exosuitSensorTrigger.get());

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? ((ItemCAD) stack.getItem()).getSpellColor(stack) : 0xFFFFFFFF,
				ModItems.cad.get());

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex != 1 ? -1 : ((ItemCADColorizer) stack.getItem()).getColor(stack),
				ModItems.cadColorizerWhite.get(),
				ModItems.cadColorizerOrange.get(),
				ModItems.cadColorizerMagenta.get(),
				ModItems.cadColorizerLightBlue.get(),
				ModItems.cadColorizerYellow.get(),
				ModItems.cadColorizerLime.get(),
				ModItems.cadColorizerPink.get(),
				ModItems.cadColorizerGray.get(),
				ModItems.cadColorizerLightGray.get(),
				ModItems.cadColorizerCyan.get(),
				ModItems.cadColorizerPurple.get(),
				ModItems.cadColorizerBlue.get(),
				ModItems.cadColorizerBrown.get(),
				ModItems.cadColorizerGreen.get(),
				ModItems.cadColorizerRed.get(),
				ModItems.cadColorizerBlack.get(),
				ModItems.cadColorizerRainbow.get(),
				ModItems.cadColorizerPsi.get(),
				ModItems.cadColorizerEmpty.get());
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

	private static void registerItemProperties() {
		ResourceLocation activeProperty = Psi.location("active");
		ClampedItemPropertyFunction hasSpellPredicate = (stack, level, entity, seed) -> ISpellAcceptor.hasSpell(stack) ? 1.0F : 0.0F;
		ItemProperties.register(ModItems.spellBullet.get(), activeProperty, hasSpellPredicate);
		ItemProperties.register(ModItems.chargeSpellBullet.get(), activeProperty, hasSpellPredicate);
		ItemProperties.register(ModItems.projectileSpellBullet.get(), activeProperty, hasSpellPredicate);
		ItemProperties.register(ModItems.loopSpellBullet.get(), activeProperty, hasSpellPredicate);
		ItemProperties.register(ModItems.circleSpellBullet.get(), activeProperty, hasSpellPredicate);
		ItemProperties.register(ModItems.grenadeSpellBullet.get(), activeProperty, hasSpellPredicate);
		ItemProperties.register(ModItems.mineSpellBullet.get(), activeProperty, hasSpellPredicate);
		ItemProperties.register(ModItems.flashRing.get(), activeProperty, hasSpellPredicate);
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
