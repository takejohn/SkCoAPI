package io.github.takejohn.skcoapi.elements.sections;

import io.github.takejohn.skcoapi.util.DetailPerformance;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CoreProtectPerformanceOptionEvent extends Event {

    public static final @NotNull String NAME = "CoreProtect where";

    private final @NotNull DetailPerformance.OptionSet options = new DetailPerformance.OptionSet();

    public DetailPerformance.OptionSet options() {
        return options;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        throw new UnsupportedOperationException();
    }

}
