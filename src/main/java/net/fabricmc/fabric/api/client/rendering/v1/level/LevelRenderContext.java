package net.fabricmc.fabric.api.client.rendering.v1.level;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.LevelRenderState;

public interface LevelRenderContext {
    LevelRenderState levelState();

    PoseStack poseStack();

    SubmitNodeCollector submitNodeCollector();
}
