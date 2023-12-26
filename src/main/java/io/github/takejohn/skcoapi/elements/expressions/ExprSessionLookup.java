package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.util.Timespans;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@Name("Lookup Sessions")
@Description("Performs a session lookup on a single player.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprSessionLookup extends PerformExpression {

    public static final String PATTERN = "lookup session[s][ (data|logs)] for %string% in[ last] %timespan%";

    private Expression<String> user;

    private Expression<Timespan> time;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        user = (Expression<String>)exprs[0];
        time = (Expression<Timespan>)exprs[1];
        return true;
    }

    @Override
    public @NotNull String toString(@org.jetbrains.annotations.Nullable Event e, boolean debug) {
        return "lookup sessions for " + user.toString(e, debug) + " in " + time.toString(e, debug);
    }

    @Override
    protected @Nullable List<String[]> perform(@NotNull Event e) {
        return SkCoAPI.coreProtectAPI.sessionLookup(user.getSingle(e),
                Timespans.toSeconds(Objects.requireNonNull(time.getSingle(e))));
    }

    public static void register() {
        register(ExprSessionLookup.class, PATTERN);
    }

}
