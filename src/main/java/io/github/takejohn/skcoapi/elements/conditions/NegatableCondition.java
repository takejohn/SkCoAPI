package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class NegatableCondition extends Condition {

    @Override
    public final boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        setNegated(isNegativeCondition(matchedPattern, parseResult));
        return afterSetNegated(exprs, matchedPattern, isDelayed, parseResult);
    }

    protected boolean isNegativeCondition(int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        return parseResult.mark != 0;
    }

    protected abstract boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern,
                                               @NotNull Kleenean isDelayed,
                                               SkriptParser.@NotNull ParseResult parseResult);

    @Override
    public final boolean check(@NotNull Event e) {
        if (isNegated()) {
            return !checkBeforeNegated(e);
        } else {
            return checkBeforeNegated(e);
        }
    }

    protected abstract boolean checkBeforeNegated(@NotNull Event e);

}
