package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.elements.conditions.CondLoggingSucceeded;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An effect which logs that a user did something at a location.
 */
public abstract class LogLocationEffect extends Effect {

    protected Expression<String> user;

    protected Expression<Location> location;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        user = (Expression<String>)exprs[0];
        location = (Expression<Location>)exprs[1];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "log that " + user.toString(e, debug) + " " + action() + " at " + location.toString(e, debug);
    }

    @Override
    protected void execute(@NotNull Event e) {
        final String singleUser = user.getSingle(e);
        final Location singleLocation = location.getSingle(e);
        CondLoggingSucceeded.set(singleUser != null && singleLocation != null && log(singleUser, singleLocation));
    }

    protected abstract @NotNull String action();

    protected abstract boolean log(@NotNull String user, @NotNull Location location);

}
