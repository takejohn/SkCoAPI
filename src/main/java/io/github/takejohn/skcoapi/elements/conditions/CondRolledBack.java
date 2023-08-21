package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class CondRolledBack extends NegatableCondition {

    public static final String PATTERN = "%coreprotectlog% is[1¦(n't| not)] rolled back";

    private Expression<CoreProtectAPI.ParseResult> log;

    @SuppressWarnings("unchecked")
    @Override
    protected boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                                      SkriptParser.@NotNull ParseResult parseResult) {
        log = (Expression<CoreProtectAPI.ParseResult>)exprs[0];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return log.toString(e, debug) + (isNegated() ? " is not rolled back" : " is rolled back");
    }

    @Override
    public boolean checkBeforeNegated(@NotNull Event e) {
        final CoreProtectAPI.ParseResult singleLog = log.getSingle(e);
        if (singleLog == null) {
            return false;
        }
        return singleLog.isRolledBack();
    }

}
