package net.fabricmc.fabric.api.client.rendering.v1;

/**
 * Implemented by vanilla render states through a mixin, so that extra data can be attached to a
 * render state the way Fabric API allows.
 */
public interface FabricRenderState {
    <T> T getData(RenderStateDataKey<T> key);

    <T> void setData(RenderStateDataKey<T> key, T value);
}
