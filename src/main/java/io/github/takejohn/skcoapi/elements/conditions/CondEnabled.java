package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("CoreProtect Is Enabled")
@Description("Checks if the server has the CoreProtect API enabled, and false if it does not.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class CondEnabled extends NegatableCondition {

    public static final String PATTERN =
            "[the] CoreProtect API is[1¦(n't| not)] (enabled|1¦disabled)";

    @Override
    protected boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                                      SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "CoreProtect API enabled";
    }

    @Override
    public boolean checkBeforeNegated(@NotNull Event e) {
        return SkCoAPI.getCoreProtectAPI().isEnabled();
    }

}
