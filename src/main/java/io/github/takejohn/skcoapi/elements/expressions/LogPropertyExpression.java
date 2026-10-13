package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import io.github.takejohn.skcoapi.elements.types.CoreProtectLogs;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

import java.util.function.Supplier;

public abstract class LogPropertyExpression<T> extends SimplePropertyExpression<CoreProtectAPI.ParseResult, T> {

    /**
     * Registers a property of CoreProtect logs with the patterns
     * {@code property of %coreprotectlog%} and {@code %coreprotectlog%'[s] property}.
     */
    protected static <E extends LogPropertyExpression<T>, T> void registerProperty(
            @NotNull SyntaxRegistry registry, @NotNull Class<E> c, @NotNull Class<T> type,
            @NotNull String property, @NotNull Supplier<E> supplier) {
        registry.register(SyntaxRegistry.EXPRESSION,
                infoBuilder(c, type, property, CoreProtectLogs.CODE_NAME, false)
                        .supplier(supplier)
                        .build());
    }

}
