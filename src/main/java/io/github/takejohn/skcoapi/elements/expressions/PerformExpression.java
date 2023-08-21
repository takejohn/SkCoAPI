package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;
import io.github.takejohn.skcoapi.SkCoAPI;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class PerformExpression extends SimpleExpression<CoreProtectAPI.ParseResult> {

    @Override
    public @NotNull Class<? extends CoreProtectAPI.ParseResult> getReturnType() {
        return CoreProtectAPI.ParseResult.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    protected final @org.eclipse.jdt.annotation.Nullable CoreProtectAPI.ParseResult[] get(@NotNull Event e) {
        final @Nullable List<String[]> results = perform(e);
        if (results == null) {
            return null;
        }
        final int resultsSize = results.size();
        final CoreProtectAPI.ParseResult[] parseResults = new CoreProtectAPI.ParseResult[resultsSize];
        for (int i = 0 ; i < resultsSize ; i++) {
            parseResults[i] = SkCoAPI.coreProtectAPI.parseResult(results.get(i));
        }
        return parseResults;
    }

    protected abstract @Nullable List<String[]> perform(@NotNull Event e);

    public static void register(@NotNull Class<? extends PerformExpression>c, @NotNull String pattern) {
        Skript.registerExpression(c, CoreProtectAPI.ParseResult.class, ExpressionType.COMBINED, pattern);
    }

}
