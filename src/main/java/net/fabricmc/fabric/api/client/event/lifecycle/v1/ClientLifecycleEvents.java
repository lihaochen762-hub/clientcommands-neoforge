package net.fabricmc.fabric.api.client.event.lifecycle.v1;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;

/**
 * NeoForge implementation of Fabric API's client lifecycle events.
 *
 * <p>The Forgified Fabric API lifecycle module cannot be used on Minecraft 26.2, because its
 * mixins no longer match the vanilla code. The only piece of it that the other Forgified Fabric
 * API modules need is this class, so it is provided here and fired from the mod entry point.
 */
public final class ClientLifecycleEvents {
    public static final Event<ClientStarted> CLIENT_STARTED = EventFactory.createArrayBacked(ClientStarted.class,
        listeners -> client -> {
            for (ClientStarted listener : listeners) {
                listener.onClientStarted(client);
            }
        });

    private ClientLifecycleEvents() {
    }

    @FunctionalInterface
    public interface ClientStarted {
        void onClientStarted(Minecraft client);
    }
}
