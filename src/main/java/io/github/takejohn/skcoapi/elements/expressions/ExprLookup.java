package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.util.DetailPerformance;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("Lookup")
@Description({
        "Performs a lookup.",
        "Objects specified with `on` and `expect on` can be Entity Types, Item Types or Block Data."
})
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprLookup extends PerformExpression {

    private static final DetailPerformance LOOKUP = DetailPerformance.LOOKUP;

    private DetailPerformance.OptionExpressions opt;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        opt = new DetailPerformance.OptionExpressions(exprs);
        return true;
    }

    @Override
    public @NotNull String toString(@org.eclipse.jdt.annotation.Nullable Event e, boolean debug) {
        return LOOKUP.toString(opt, e, debug);
    }

    @Override
    protected @Nullable List<String[]> perform(@NotNull Event e) {
        return LOOKUP.perform(opt, e);
    }

    public static void register() {
        Skript.registerExpression(ExprLookup.class, CoreProtectAPI.ParseResult.class, ExpressionType.COMBINED,
                LOOKUP.syntaxPattern());
    }

}
