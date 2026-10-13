package io.github.takejohn.skcoapi.util;

import io.github.takejohn.skcoapi.SkCoAPI;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ParseResults {

    public static final CoreProtectAPI.@NotNull ParseResult @NotNull[] EMPTY = new CoreProtectAPI.ParseResult[0];

    @Contract("-> fail")
    private ParseResults() {
        throw new AssertionError(getClass().getName() + " is a util class");
    }

    public static CoreProtectAPI.@NotNull ParseResult @NotNull[] parseResults(@Nullable List<String[]> results) {
        if (results == null || results.isEmpty()) {
            return EMPTY;
        }
        final CoreProtectAPI api = SkCoAPI.getCoreProtectAPI();
        final int resultsSize = results.size();
        final CoreProtectAPI.ParseResult[] parseResults = new CoreProtectAPI.ParseResult[resultsSize];
        for (int i = 0 ; i < resultsSize ; i++) {
            parseResults[i] = api.parseResult(results.get(i));
        }
        return parseResults;
    }

}
