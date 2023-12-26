package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("CoreProtect API Version")
@Description("The version of CoreProtect API.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprAPIVersion extends SimpleExpression<Integer> {

    protected static final String[] PATTERNS = new String[]{
            "[the ]CoreProtect API['s] version",
            "[the ]version of CoreProtect API"
    };

    @Override
    public @NotNull Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "CoreProtect API version";
    }

    @Override
    protected @NotNull Integer @NotNull[] get(@NotNull Event e) {
        return new Integer[]{SkCoAPI.coreProtectAPI.APIVersion()};
    }

    public static void register() {
        Skript.registerExpression(ExprAPIVersion.class, Integer.class, ExpressionType.SIMPLE, PATTERNS);
    }

}
