package io.github.takejohn.skcoapi.elements.sections;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoreProtectPerformanceCompleteEvent extends Event {

    public static final @NotNull String NAME = "CoreProtect performance complete";

    private final @Nullable CoreProtectAPI.ParseResult[] results;

    CoreProtectPerformanceCompleteEvent(@Nullable CoreProtectAPI.ParseResult[] results) {
        this.results = results;
    }

    public CoreProtectAPI.ParseResult[] getResults() {
        return results;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        throw new UnsupportedOperationException();
    }

}
