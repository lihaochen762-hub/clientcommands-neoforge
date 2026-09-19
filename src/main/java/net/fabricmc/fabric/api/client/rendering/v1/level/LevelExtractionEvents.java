package net.fabricmc.fabric.api.client.rendering.v1.level;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class LevelExtractionEvents {
    public static final Event<EndExtraction> END_EXTRACTION = EventFactory.createArrayBacked(EndExtraction.class,
        listeners -> context -> {
            for (EndExtraction listener : listeners) {
                listener.endExtraction(context);
            }
        });

    private LevelExtractionEvents() {
    }

    @FunctionalInterface
    public interface EndExtraction {
        void endExtraction(LevelExtractionContext context);
    }
}
