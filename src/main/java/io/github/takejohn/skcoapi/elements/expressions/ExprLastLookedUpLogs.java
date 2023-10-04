package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("Last Restored Logs")
@Description("The logs lastly performed lookup through a lookup effect.")
@Since("0.2.1")
@RequiredPlugins("CoreProtect")
public class ExprLastLookedUpLogs extends PerformExpression {

    private static final String PATTERN = "[the ][last[ly] ]looked up (data|logs)";

    private static List<String[]> results;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public @NotNull String toString(@org.eclipse.jdt.annotation.Nullable Event e, boolean debug) {
        return "last looked up logs";
    }

    @Override
    protected @Nullable List<String[]> perform(@NotNull Event e) {
        synchronized (ExprLastLookedUpLogs.class) {
            return results;
        }
    }

    public static synchronized void set(List<String[]> results) {
        ExprLastLookedUpLogs.results = results;
    }

    public static void register() {
        register(ExprLastLookedUpLogs.class, ExpressionType.SIMPLE, PATTERN);
    }

}
