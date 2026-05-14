package net.neoforged.neoforge.client.event;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.util.function.Consumer;

public class RegisterShadersEvent {
	public ResourceProvider getResourceProvider() {
		return null;
	}

	public void registerShader(ShaderInstance shader, Consumer<ShaderInstance> callback) {
		callback.accept(shader);
	}
}
