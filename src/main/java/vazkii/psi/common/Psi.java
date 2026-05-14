/*
 * This class is distributed as part of the Psi Mod.
 * Get the Source Code in GitHub:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: https://psi.vazkii.net/license.php
 */
package vazkii.psi.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.material.PsimetalArmorMaterial;
import vazkii.psi.client.fx.ModParticles;
import vazkii.psi.common.attribute.base.ModAttributes;
import vazkii.psi.common.block.base.ModBlocks;
import vazkii.psi.common.core.PsiCreativeTab;
import vazkii.psi.common.core.handler.ContributorSpellCircleHandler;
import vazkii.psi.common.core.handler.InternalMethodHandler;
import vazkii.psi.common.core.proxy.IProxy;
import vazkii.psi.common.core.proxy.ServerProxy;
import vazkii.psi.common.crafting.ModCraftingRecipes;
import vazkii.psi.common.item.base.ModDataComponents;
import vazkii.psi.common.item.base.ModItems;
import vazkii.psi.common.item.component.DefaultStats;
import vazkii.psi.common.network.MessageRegister;
import vazkii.psi.common.spell.base.ModSpellPieces;

public class Psi implements ModInitializer {

	public static final Logger logger = LogManager.getLogger(PsiAPI.MOD_ID);

	public static boolean magical;
	public static IProxy proxy;

	@Override
	public void onInitialize() {
		ModAttributes.ATTRIBUTES.register();
		ModDataComponents.DATA_COMPONENT_TYPES.register();
		PsimetalArmorMaterial.ARMOR_MATERIALS.register();
		ModCraftingRecipes.RECIPE_TYPES.register();
		ModCraftingRecipes.RECIPE_SERIALIZERS.register();
		ModCraftingRecipes.CONDITION_CODECS.register();
		ModParticles.PARTICLE_TYPES.register();
		ModBlocks.BLOCKS.register();
		ModBlocks.BLOCK_TYPES.register();
		ModBlocks.MENU.register();
		ModItems.ITEMS.register();
		PsiCreativeTab.register();
		ModSpellPieces.SPELL_PIECES.register();
		ModSpellPieces.ADVANCEMENT_GROUPS.register();
		MessageRegister.register();
		commonSetup();
		proxy = new ServerProxy();
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(PsiAPI.MOD_ID, path);
	}

	private void commonSetup() {
		magical = FabricLoader.getInstance().isModLoaded("magipsi");
		PsiAPI.internalHandler = new InternalMethodHandler();

		ContributorSpellCircleHandler.firstStart();
		DefaultStats.registerStats();
	}

}
