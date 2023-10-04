package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.elements.expressions.ExprLastLookedUpLogs;
import io.github.takejohn.skcoapi.util.DetailPerformance;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Lookup")
@Description({
        "Performs a lookup.",
        "This effect causes a delay of the script execution.",
        "Objects specified with `on` and `expect on` can be Entity Data, Entity Types, Item Types or Block Data."
})
@Since("0.2.1")
@RequiredPlugins("CoreProtect")
public class EffLookup extends AsyncEffect {

    private static final DetailPerformance LOOKUP = DetailPerformance.LOOKUP;

    protected DetailPerformance.OptionExpressions opt;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        getParser().setHasDelayBefore(Kleenean.TRUE);
        opt = new DetailPerformance.OptionExpressions(exprs);
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return LOOKUP.toString(opt, e, debug);
    }

    @Override
    protected void execute(@NotNull Event e) {
        ExprLastLookedUpLogs.set(LOOKUP.perform(opt, e));
    }

    public static void register() {
        Skript.registerEffect(EffLookup.class, LOOKUP.syntaxPattern());
    }

}
