package io.github.takejohn.skcoapi.elements.sections;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * A pseudo event in which the body of a lookup / rollback / restore section runs.
 * This event is never called through Bukkit.
 */
public class CoreProtectPerformanceCompleteEvent extends Event {

    public static final @NotNull String NAME = "CoreProtect performance complete";

    private static final HandlerList HANDLERS = new HandlerList();

    private final CoreProtectAPI.@NotNull ParseResult @NotNull[] results;

    CoreProtectPerformanceCompleteEvent(CoreProtectAPI.@NotNull ParseResult @NotNull[] results) {
        this.results = results;
    }

    public CoreProtectAPI.@NotNull ParseResult @NotNull[] getResults() {
        return results;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

}
