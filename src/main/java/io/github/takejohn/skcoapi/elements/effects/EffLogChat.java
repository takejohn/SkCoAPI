package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.elements.conditions.CondLoggingSucceeded;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Log Said")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffLogChat extends Effect {

    public static final String PATTERN = "log [that] %player% (said|sent [the] message) %string%";

    private Expression<Player> player;

    private Expression<String> message;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        player = (Expression<Player>)exprs[0];
        message = (Expression<String>)exprs[1];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "log that " + player.toString(e, debug) + " said " + message.toString(e, debug);
    }

    @Override
    protected void execute(@NotNull Event e) {
        CondLoggingSucceeded.set(SkCoAPI.coreProtectAPI.logChat(player.getSingle(e), message.getSingle(e)));
    }

}
