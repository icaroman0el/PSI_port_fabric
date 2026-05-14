package net.neoforged.neoforge.client.event;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Function;

public class RegisterParticleProvidersEvent {
	public <T extends ParticleOptions> void registerSpriteSet(ParticleType<T> type, Function<SpriteSet, ParticleProvider<T>> provider) {
	}
}
