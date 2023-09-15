package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.util.Timespans;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Purge CoreProtect Data")
@Description("Performs a purge on the CoreProtect database.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffPurge extends Effect {

    public static final String PATTERN = "(purge|delete)[ (any|all)] CoreProtect (data|logs) older than %timespan%";

    private static final Timespan TWENTY_FOUR_HOURS = new Timespan(1000L * 60L * 60L * 24L);

    private Expression<Timespan> time;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        time = (Expression<Timespan>)exprs[0];
        if (time instanceof Literal && ((Literal<Timespan>)time).getSingle().compareTo(TWENTY_FOUR_HOURS) < 0) {
            Skript.warning("You can only purge data older than 24 hours.");
        }
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
