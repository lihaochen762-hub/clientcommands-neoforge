package net.fabricmc.fabric.api.client.rendering.v1.level;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class LevelRenderEvents {
    public static final Event<EndExtraction> END_EXTRACTION = EventFactory.createArrayBacked(EndExtraction.class,
        listeners -> context -> {
            for (EndExtraction listener : listeners) {
                listener.endExtraction(context);
            }
        });

    public static final Event<EndMain> END_MAIN = EventFactory.createArrayBacked(EndMain.class,
        listeners -> context -> {
            for (EndMain listener : listeners) {
                listener.endMain(context);
            }
        });

    private LevelRenderEvents() {
    }

    @FunctionalInterface
    public interface EndExtraction {
        void endExtraction(LevelExtractionContext context);
    }

    @FunctionalInterface
    public interface EndMain {
        void endMain(LevelRenderContext context);
    }
}
