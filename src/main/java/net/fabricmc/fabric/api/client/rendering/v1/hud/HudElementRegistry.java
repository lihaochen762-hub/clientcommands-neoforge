package net.fabricmc.fabric.api.client.rendering.v1.hud;

import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * NeoForge implementation of Fabric API's HUD element registry, backed by NeoForge's GUI layers.
 */
public interface HudElementRegistry {
    List<Entry> FIRST = new ArrayList<>();
    List<Entry> LAST = new ArrayList<>();

    // Fabric API declares this registry as an interface, so it has to stay an interface here too:
    // callers are compiled against an interface static method.
    static void addFirst(Identifier id, HudElement element) {
        FIRST.add(new Entry(id, element));
    }

    static void addLast(Identifier id, HudElement element) {
        LAST.add(new Entry(id, element));
    }

    static void registerLayers(RegisterGuiLayersEvent event) {
        for (Entry entry : FIRST) {
            event.registerBelowAll(entry.id(), entry.element()::extractRenderState);
        }
        for (Entry entry : LAST) {
            event.registerAboveAll(entry.id(), entry.element()::extractRenderState);
        }
    }

    record Entry(Identifier id, HudElement element) {
    }
}
