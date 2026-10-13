package io.github.takejohn.skcoapi.util;

import ch.njol.skript.util.Timespan;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Timespans {

    @Contract("-> fail")
    private Timespans() {
        throw new AssertionError(getClass().getName() + " is a util class");
    }

    /**
     * Converts a timespan into seconds, as CoreProtect API expects.
     * Timespans too long for an {@code int} (including infinite ones) are clamped to {@link Integer#MAX_VALUE}.
     */
    public static int toSeconds(@NotNull Timespan timespan) {
        return (int)Math.min(timespan.getAs(Timespan.TimePeriod.SECOND), Integer.MAX_VALUE);
    }

}
