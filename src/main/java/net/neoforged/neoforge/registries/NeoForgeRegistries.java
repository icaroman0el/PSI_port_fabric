package net.neoforged.neoforge.registries;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public final class NeoForgeRegistries {
	private NeoForgeRegistries() {}

	public static final class Keys {
		public static final ResourceKey<Registry<MapCodec<? extends ICondition>>> CONDITION_CODECS =
				ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("neoforge", "condition_codecs"));

		private Keys() {}
	}
}
