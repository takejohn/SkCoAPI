package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Test CoreProtect API")
@Description("Prints out \"[CoreProtect] API Test Successful.\" in the server console.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffTestAPI extends Effect {

    public static final String PATTERN = "test [the] CoreProtect API";

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern,
                        @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    protected void execute(@NotNull Event e) {
        SkCoAPI.getCoreProtectAPI().testAPI();
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "test CoreProtect API";
    }

}
