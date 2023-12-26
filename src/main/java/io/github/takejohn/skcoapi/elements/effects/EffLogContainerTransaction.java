package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.elements.conditions.CondLoggingSucceeded;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Log Container Transaction")
@Description("Logs any transactions made to a block's inventory immediately after calling the method.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffLogContainerTransaction extends Effect {

    public static final String PATTERN = "log [that] %string% made [a] transaction [to [a] container] at %location%";

    private Expression<String> user;

    private Expression<Location> location;

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
        return "log that " + user.toString(e, debug) + " made a transaction to a container at " +
                location.toString(e, debug);
    }

    @Override
    protected void execute(@NotNull Event e) {
        CondLoggingSucceeded.set(SkCoAPI.coreProtectAPI.logContainerTransaction(user.getSingle(e), location.getSingle(e)));
    }

}
