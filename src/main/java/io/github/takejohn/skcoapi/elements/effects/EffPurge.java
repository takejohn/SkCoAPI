package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.util.Timespans;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class EffPurge extends Effect {

    public static final String PATTERN = "(purge|delete)[ (any|all)] CoreProtect (data|logs) older than %timespan%";

    private Expression<Timespan> time;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        time = (Expression<Timespan>)exprs[0];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "purge CoreProtect logs older than " + time.toString(e, debug);
    }

    @Override
    protected void execute(@NotNull Event e) {
        final Timespan singleTime = time.getSingle(e);
        if (singleTime != null) {
            SkCoAPI.coreProtectAPI.performPurge(Timespans.toSeconds(singleTime));
        }
    }

}
