package net.neoforged.neoforge.common.extensions;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Inventory;

public final class IMenuTypeExtension {
	private IMenuTypeExtension() {}

	public static <T extends AbstractContainerMenu> MenuType<T> create(MenuType.MenuSupplier<T> supplier) {
		return new MenuType<>(supplier, FeatureFlags.DEFAULT_FLAGS);
	}

	public static <T extends AbstractContainerMenu> MenuType<T> create(Factory<T> factory) {
		return new MenuType<>((containerId, inventory) -> factory.create(containerId, inventory, null), FeatureFlags.DEFAULT_FLAGS);
	}

	@FunctionalInterface
	public interface Factory<T extends AbstractContainerMenu> {
		T create(int containerId, Inventory inventory, FriendlyByteBuf buf);
	}
}
