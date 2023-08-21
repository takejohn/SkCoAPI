package io.github.takejohn.skcoapi.util;

import ch.njol.skript.util.Timespan;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Timespans {

    @Contract("-> fail")
    private Timespans() {
        throw new AssertionError(getClass().getName() + " is a util class");
    }

    public static int toSeconds(@NotNull Timespan timespan) {
        return (int)(timespan.getMilliSeconds() / 1000L);
    }

}
