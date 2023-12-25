package io.github.takejohn.skcoapi.elements.sections;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CoreProtectPerformanceCompleteEvent extends Event {

    public static final @NotNull String NAME = "CoreProtect performance complete";

    @Override
    public @NotNull HandlerList getHandlers() {
        throw new UnsupportedOperationException();
    }

}
