package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class CondEnabled extends NegatableCondition {

    public static final String PATTERN =
            "[the] CoreProtect API is[1¦(n't| not)] (enabled|1¦disabled)";

    @Override
    protected boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                                      SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "CoreProtect API enabled";
    }

    @Override
    public boolean checkBeforeNegated(@NotNull Event e) {
        return SkCoAPI.coreProtectAPI.isEnabled();
    }

}
