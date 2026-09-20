package net.fabricmc.fabric.api.client.rendering.v1.hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * NeoForge implementation of Fabric API's HUD element. The method signature matches NeoForge's
 * {@code GuiLayer}, which is what it is registered as.
 */
@FunctionalInterface
public interface HudElement {
    void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker);
}
