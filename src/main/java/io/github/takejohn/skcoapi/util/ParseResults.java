package io.github.takejohn.skcoapi.util;

import io.github.takejohn.skcoapi.SkCoAPI;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ParseResults {

    @Contract("-> fail")
    private ParseResults() {
        throw new AssertionError(getClass().getName() + " is a util class");
    }

    public static CoreProtectAPI.@NotNull ParseResult @NotNull[] parseResults(@Nullable List<String[]> results) {
        if (results == null) {
            return new CoreProtectAPI.ParseResult[0];
        }
        final int resultsSize = results.size();
        final CoreProtectAPI.ParseResult[] parseResults = new CoreProtectAPI.ParseResult[resultsSize];
        for (int i = 0 ; i < resultsSize ; i++) {
            parseResults[i] = SkCoAPI.getCoreProtectAPI().parseResult(results.get(i));
        }
        return parseResults;
    }

}
