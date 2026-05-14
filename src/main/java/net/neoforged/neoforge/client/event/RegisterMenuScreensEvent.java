package net.neoforged.neoforge.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class RegisterMenuScreensEvent {
	public <M extends AbstractContainerMenu, U extends net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<M>> void register(MenuType<? extends M> type, MenuScreens.ScreenConstructor<M, U> factory) {
	}
}
