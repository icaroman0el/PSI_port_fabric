/*
 * This class is distributed as part of the Psi Mod.
 * Get the Source Code in GitHub:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: https://psi.vazkii.net/license.php
 */
package vazkii.psi.client.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public final class ClientNetworkHelper {
	private ClientNetworkHelper() {}

	public static <MSG extends CustomPacketPayload> void sendToServer(MSG message) {
		if(ClientPlayNetworking.canSend(message.type())) {
			ClientPlayNetworking.send(message);
		}
	}
}
