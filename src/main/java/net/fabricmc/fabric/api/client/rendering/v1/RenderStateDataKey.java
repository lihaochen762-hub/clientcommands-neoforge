package net.fabricmc.fabric.api.client.rendering.v1;

import java.util.function.Supplier;

/**
 * NeoForge implementation of Fabric API's render state data key.
 *
 * <p>The Forgified Fabric API rendering module cannot run on Minecraft 26.2, so the parts of it
 * that this mod needs are provided here instead.
 */
public final class RenderStateDataKey<T> {
    private final Supplier<String> name;

    private RenderStateDataKey(Supplier<String> name) {
        this.name = name;
    }

    public static <T> RenderStateDataKey<T> create(Supplier<String> name) {
        return new RenderStateDataKey<>(name);
    }

    public static <T> RenderStateDataKey<T> create() {
        return create(() -> "unnamed");
    }

    @Override
    public String toString() {
        return this.name.get();
    }
}
