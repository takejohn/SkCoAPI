package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprAPIVersion extends SimpleExpression<Integer> {

    protected static final String[] PATTERNS = new String[]{
            "[the ]CoreProtect API['s] version",
            "[the ]version of CoreProtect API"
    };

    @Override
    public @NotNull Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "CoreProtect API version";
    }

    @Override
    protected @Nullable Integer[] get(@NotNull Event e) {
        return new Integer[]{SkCoAPI.coreProtectAPI.APIVersion()};
    }

    public static void register() {
        Skript.registerExpression(ExprAPIVersion.class, Integer.class, ExpressionType.SIMPLE, PATTERNS);
    }

}
