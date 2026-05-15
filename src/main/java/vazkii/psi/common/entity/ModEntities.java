/*
 * This class is distributed as part of the Psi Mod.
 * Get the Source Code in GitHub:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: https://psi.vazkii.net/license.php
 */
package vazkii.psi.common.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import vazkii.psi.common.Psi;
import vazkii.psi.common.lib.LibEntityNames;

import static net.minecraft.world.entity.MobCategory.MISC;

public final class ModEntities {
	public static EntityType<EntitySpellProjectile> spellProjectile;
	public static EntityType<EntitySpellCircle> spellCircle;
	public static EntityType<EntitySpellGrenade> spellGrenade;
	public static EntityType<EntitySpellCharge> spellCharge;
	public static EntityType<EntitySpellMine> spellMine;

	@SuppressWarnings("unchecked")
	private static <T extends Entity> EntityType<T> build(EntityType.Builder<T> builder) {
		return ((FabricEntityType.Builder<T>) builder).build();
	}

	public static void register() {
		if(spellProjectile != null) {
			return;
		}

		spellProjectile = Registry.register(BuiltInRegistries.ENTITY_TYPE, Psi.location(LibEntityNames.SPELL_PROJECTILE),
				build(EntityType.Builder.of((EntityType.EntityFactory<EntitySpellProjectile>) EntitySpellProjectile::new, MISC)
						.clientTrackingRange(256)
						.updateInterval(10)
						.alwaysUpdateVelocity(true)
						.sized(0.0F, 0.0F)));
		spellCircle = Registry.register(BuiltInRegistries.ENTITY_TYPE, Psi.location(LibEntityNames.SPELL_CIRCLE),
				build(EntityType.Builder.of(EntitySpellCircle::new, MISC)
						.clientTrackingRange(256)
						.updateInterval(10)
						.alwaysUpdateVelocity(false)
						.sized(3.0f, 0.3f)
						.fireImmune()));
		spellGrenade = Registry.register(BuiltInRegistries.ENTITY_TYPE, Psi.location(LibEntityNames.SPELL_GRENADE),
				build(EntityType.Builder.of((EntityType.EntityFactory<EntitySpellGrenade>) EntitySpellGrenade::new, MISC)
						.clientTrackingRange(256)
						.updateInterval(10)
						.alwaysUpdateVelocity(true)
						.sized(0.0F, 0.0F)));
		spellCharge = Registry.register(BuiltInRegistries.ENTITY_TYPE, Psi.location(LibEntityNames.SPELL_CHARGE),
				build(EntityType.Builder.of((EntityType.EntityFactory<EntitySpellCharge>) EntitySpellCharge::new, MISC)
						.clientTrackingRange(256)
						.updateInterval(10)
						.alwaysUpdateVelocity(true)
						.sized(0.0F, 0.0F)));
		spellMine = Registry.register(BuiltInRegistries.ENTITY_TYPE, Psi.location(LibEntityNames.SPELL_MINE),
				build(EntityType.Builder.of((EntityType.EntityFactory<EntitySpellMine>) EntitySpellMine::new, MISC)
						.clientTrackingRange(256)
						.updateInterval(10)
						.alwaysUpdateVelocity(true)
						.sized(0.0F, 0.0F)));
	}
}
