package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.elements.sections.CoreProtectPerformanceCompleteEvent;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Results")
@Events("CoreProtect performance complete")
@Description({
        "Usable only in lookup / rollback / restore effect sections.",
        "Returns the result of the lookup."
})
@Since("0.3.0")
@RequiredPlugins("CoreProtect")
public class ExprResults extends SimpleExpression<CoreProtectAPI.ParseResult> {

    private static final String PATTERN = "[CoreProtect] results";

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION,
                SyntaxInfo.Expression.builder(ExprResults.class, CoreProtectAPI.ParseResult.class)
                        .priority(SyntaxInfo.SIMPLE)
                        .supplier(ExprResults::new)
                        .addPattern(PATTERN)
                        .build());
    }

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
        return "CoreProtect results";
    }

    @Override
    protected CoreProtectAPI.@NotNull ParseResult @NotNull[] get(@NotNull Event e) {
        if (!(e instanceof CoreProtectPerformanceCompleteEvent completeEvent)) {
            return new CoreProtectAPI.ParseResult[0];
        }
        return completeEvent.getResults();
    }

}
