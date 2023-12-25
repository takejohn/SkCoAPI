package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;
import io.github.takejohn.skcoapi.util.ParseResults;
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
    protected final CoreProtectAPI.@NotNull ParseResult @NotNull[] get(@NotNull Event e) {
        return ParseResults.parseResults(perform(e));
    }

    protected abstract @Nullable List<String[]> perform(@NotNull Event e);

    public static void register(@NotNull Class<? extends PerformExpression>c, @NotNull String pattern) {
        Skript.registerExpression(c, CoreProtectAPI.ParseResult.class, ExpressionType.COMBINED, pattern);
    }

    public static void register(@NotNull Class<? extends PerformExpression>c, ExpressionType type, @NotNull String pattern) {
        Skript.registerExpression(c, CoreProtectAPI.ParseResult.class, type, pattern);
    }

}
