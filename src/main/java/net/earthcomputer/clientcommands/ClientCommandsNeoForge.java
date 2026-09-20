package net.earthcomputer.clientcommands;

import dev.xpple.betterconfig.BetterConfig;
import dev.xpple.betterconfig.BetterConfigClient;
import dev.xpple.clientarguments.ClientArguments;
import net.earthcomputer.clientcommands.server.ClientCommandsServer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.common.NeoForge;
import dev.xpple.simplewaypoints.SimpleWaypoints;

/**
 * NeoForge entry point.
 *
 * <p>Client Commands is written against the Fabric API. On NeoForge that API is provided by the
 * Forgified Fabric API mods, which supply the API classes but do not run Fabric entry points.
 * The Fabric entry points of this mod, and of the Fabric-only libraries it embeds, are therefore
 * invoked from here in the order the Fabric loader would use.
 */
@Mod("clientcommands")
public class ClientCommandsNeoForge {
    private static boolean clientStarted;

    public ClientCommandsNeoForge(IEventBus modBus) {
        if (FMLEnvironment.getDist().isClient()) {
            new ClientArguments().onInitializeClient();
            new BetterConfigClient().onInitializeClient();
            new SimpleWaypoints().onInitializeClient();
            ClientCommands.initClient();
            modBus.addListener(RegisterGuiLayersEvent.class, HudElementRegistry::registerLayers);
            modBus.addListener(FMLClientSetupEvent.class, event -> ClientCommands.setupScrambleWindowTitle());
            // Fabric fires CLIENT_STARTED once the client's main loop is running. The closest
            // NeoForge equivalent is the first client tick.
            NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, event -> {
                if (!clientStarted) {
                    clientStarted = true;
                    ClientLifecycleEvents.CLIENT_STARTED.invoker().onClientStarted(Minecraft.getInstance());
                }
            });
        } else {
            new BetterConfig().onInitializeServer();
        }
        new ClientCommandsServer().onInitialize();
    }
}
