package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.elements.sections.CoreProtectPerformanceCompleteEvent;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Results")
@Events("CoreProtect performance complete")
@Description({
        "Usable only in lookup / rollback / restore effect sections.",
        "Returns the result of the lookup."
})
@Since("0.3.0")
public class ExprResults extends SimpleExpression<CoreProtectAPI.ParseResult> {

    private static final String PATTERN = "[CoreProtect] results";

    @Override
    public @NotNull Class<? extends CoreProtectAPI.ParseResult> getReturnType() {
        return CoreProtectAPI.ParseResult.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public boolean init(Expression<?> @NotNull[] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        if (!getParser().isCurrentEvent(CoreProtectPerformanceCompleteEvent.class)) {
            Skript.error("Cannot use result expression outside " + CoreProtectPerformanceCompleteEvent.NAME + " event");
            return false;
        }
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return PATTERN;
    }

    @Override
    protected CoreProtectAPI.@NotNull ParseResult @NotNull[] get(@NotNull Event e) {
        assert e instanceof CoreProtectPerformanceCompleteEvent;
        return ((CoreProtectPerformanceCompleteEvent)e).getResults();
    }

    public static void register() {
        Skript.registerExpression(ExprResults.class, CoreProtectAPI.ParseResult.class, ExpressionType.SIMPLE, PATTERN);
    }

}
