package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.lang.util.SimpleExpression;
import io.github.takejohn.skcoapi.util.ParseResults;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

import java.util.List;
import java.util.function.Supplier;

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
    protected final CoreProtectAPI.@NotNull ParseResult @NotNull[] get(@NotNull Event e) {
        return ParseResults.parseResults(perform(e));
    }

    protected abstract @Nullable List<String[]> perform(@NotNull Event e);

    protected static <E extends PerformExpression> void register(@NotNull SyntaxRegistry registry, @NotNull Class<E> c,
                                                                 @NotNull Supplier<E> supplier, @NotNull String pattern) {
        registry.register(SyntaxRegistry.EXPRESSION,
                SyntaxInfo.Expression.builder(c, CoreProtectAPI.ParseResult.class)
                        .priority(SyntaxInfo.COMBINED)
                        .supplier(supplier)
                        .addPattern(pattern)
                        .build());
    }

}
