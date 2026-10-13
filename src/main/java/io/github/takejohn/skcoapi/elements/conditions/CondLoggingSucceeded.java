package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Logging Succeeded")
@Description("Checks if the log said/executed/placed/broke/container transaction/interaction effect lastly ran was successful.")
@Since("0.2.0")
@RequiredPlugins("CoreProtect")
public class CondLoggingSucceeded extends NegatableCondition {

    private static final String[] PATTERNS = {
            "logging (succeeded|did(n't| not) fail)",
            "logging (failed|did(n't| not) succeed)"
    };

    private static volatile boolean result = false;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.CONDITION,
                SyntaxInfo.simple(CondLoggingSucceeded.class, CondLoggingSucceeded::new, PATTERNS));
    }

    @Override
    protected boolean isNegativeCondition(int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        return matchedPattern != 0;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        if (isNegated()) {
            return "logging failed";
        } else {
            return "logging succeeded";
        }
    }

    @Override
    protected boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                                      SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    protected boolean checkBeforeNegated(@NotNull Event e) {
        return CondLoggingSucceeded.result;
    }

    public static void set(boolean result) {
        CondLoggingSucceeded.result = result;
    }

}
