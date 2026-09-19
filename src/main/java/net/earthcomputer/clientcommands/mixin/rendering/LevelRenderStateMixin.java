package net.earthcomputer.clientcommands.mixin.rendering;

import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.IdentityHashMap;
import java.util.Map;

@Mixin(LevelRenderState.class)
public class LevelRenderStateMixin implements FabricRenderState {
    @Unique
    private final Map<RenderStateDataKey<?>, Object> clientcommands$data = new IdentityHashMap<>();

    @Override
    public <T> T getData(RenderStateDataKey<T> key) {
        return (T) this.clientcommands$data.get(key);
    }

    @Override
    public <T> void setData(RenderStateDataKey<T> key, T value) {
        this.clientcommands$data.put(key, value);
    }
}
