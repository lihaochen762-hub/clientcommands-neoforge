package net.earthcomputer.clientcommands.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.earthcomputer.clientcommands.mixin.rendering.LevelRendererAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Fires the Fabric API level rendering events that this mod uses from NeoForge's own events.
 */
public final class FabricRenderingEvents {
    private FabricRenderingEvents() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ExtractLevelRenderStateEvent.class, event -> {
            LevelExtractionContext context = new ExtractionContext(event.getRenderState(), event.getLevel(), event.getCamera(), event.getDeltaTracker());
            LevelExtractionEvents.END_EXTRACTION.invoker().endExtraction(context);
            LevelRenderEvents.END_EXTRACTION.invoker().endExtraction(context);
        });
        NeoForge.EVENT_BUS.addListener(RenderLevelStageEvent.AfterLevel.class, event -> {
            LevelRenderEvents.END_MAIN.invoker().endMain(new RenderContext(event));
        });
    }

    private record ExtractionContext(LevelRenderState levelState, ClientLevel level, Camera camera,
                                     DeltaTracker deltaTracker) implements LevelExtractionContext {
    }

    private record RenderContext(LevelRenderState levelState, PoseStack poseStack,
                                 SubmitNodeCollector submitNodeCollector) implements LevelRenderContext {
        RenderContext(RenderLevelStageEvent event) {
            this(event.getLevelRenderState(), event.getPoseStack(),
                ((LevelRendererAccessor) event.getLevelRenderer()).clientcommands$getSubmitNodeStorage());
        }
    }
}
