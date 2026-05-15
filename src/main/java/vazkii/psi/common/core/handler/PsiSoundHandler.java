/*
 * This class is distributed as part of the Psi Mod.
 * Get the Source Code in GitHub:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: https://psi.vazkii.net/license.php
 */
package vazkii.psi.common.core.handler;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import vazkii.psi.common.Psi;

public final class PsiSoundHandler {

	public static final SoundEvent bulletCreate = SoundEvent.createVariableRangeEvent(Psi.location("bullet_create"));
	public static final SoundEvent cadCreate = SoundEvent.createVariableRangeEvent(Psi.location("cad_create"));
	public static final SoundEvent cadShoot = SoundEvent.createVariableRangeEvent(Psi.location("cad_shoot"));
	public static final SoundEvent compileError = SoundEvent.createVariableRangeEvent(Psi.location("compile_error"));
	public static final SoundEvent levelUp = SoundEvent.createVariableRangeEvent(Psi.location("level_up"));
	public static final SoundEvent loopcast = SoundEvent.createVariableRangeEvent(Psi.location("loopcast"));
	public static final SoundEvent book = SoundEvent.createVariableRangeEvent(Psi.location("book"));
	public static final SoundEvent bookFlip = SoundEvent.createVariableRangeEvent(Psi.location("book_flip"));
	public static final SoundEvent bookOpen = SoundEvent.createVariableRangeEvent(Psi.location("book_open"));

	public static void registerFabricSounds() {
		Registry.register(BuiltInRegistries.SOUND_EVENT, bulletCreate.getLocation(), bulletCreate);
		Registry.register(BuiltInRegistries.SOUND_EVENT, cadCreate.getLocation(), cadCreate);
		Registry.register(BuiltInRegistries.SOUND_EVENT, cadShoot.getLocation(), cadShoot);
		Registry.register(BuiltInRegistries.SOUND_EVENT, compileError.getLocation(), compileError);
		Registry.register(BuiltInRegistries.SOUND_EVENT, levelUp.getLocation(), levelUp);
		Registry.register(BuiltInRegistries.SOUND_EVENT, loopcast.getLocation(), loopcast);
		Registry.register(BuiltInRegistries.SOUND_EVENT, book.getLocation(), book);
		Registry.register(BuiltInRegistries.SOUND_EVENT, bookFlip.getLocation(), bookFlip);
		Registry.register(BuiltInRegistries.SOUND_EVENT, bookOpen.getLocation(), bookOpen);
	}
}
