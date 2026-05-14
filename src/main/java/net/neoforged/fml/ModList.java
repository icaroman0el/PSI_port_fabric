package net.neoforged.fml;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.Optional;

public final class ModList {
	private static final ModList INSTANCE = new ModList();

	private ModList() {}

	public static ModList get() {
		return INSTANCE;
	}

	public boolean isLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}

	public Optional<Container> getModContainerById(String modid) {
		return FabricLoader.getInstance().getModContainer(modid).map(Container::new);
	}

	public record Container(ModContainer container) {
		public String getModId() {
			return container.getMetadata().getId();
		}

		public String getNamespace() {
			return container.getMetadata().getId();
		}

		public ModInfo getModInfo() {
			return new ModInfo(container);
		}
	}

	public record ModInfo(ModContainer container) {
		public String getDisplayName() {
			return container.getMetadata().getName();
		}

		public Object getVersion() {
			return container.getMetadata().getVersion();
		}
	}
}
