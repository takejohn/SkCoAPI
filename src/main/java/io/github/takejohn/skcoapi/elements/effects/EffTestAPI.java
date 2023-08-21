package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffTestAPI extends Effect {

    public static final String PATTERN = "test [the] CoreProtect API";

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern,
                        @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    protected void execute(@NotNull Event e) {
        SkCoAPI.coreProtectAPI.testAPI();
    }

    @Override
    public @NotNull String toString(@org.eclipse.jdt.annotation.Nullable Event e, boolean debug) {
        return "test CoreProtect API";
    }

}
