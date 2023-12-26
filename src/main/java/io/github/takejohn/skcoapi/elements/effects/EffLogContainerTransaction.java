package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.elements.conditions.CondLoggingSucceeded;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Log Container Transaction")
@Description("Logs any transactions made to a block's inventory immediately after calling the method.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
@Examples({
        "command /takeall:",
        "    executable by: players",
        "    trigger:",
        "        set {_block} to target block of player",
        "        send \"&aTaking items from %{_block}%\" to player",
        "        loop all items in the inventory of {_block}:",
        "            if player cannot hold loop-item:",
        "                send \"&cStopped taking items because your inventory is full!\" to player",
        "                stop",
        "            give loop-item to player",
        "            remove loop-item from the inventory of {_block}",
        "            log that player's name made a transaction to a container at location of {_block}"
})
public class EffLogContainerTransaction extends Effect {

    public static final String PATTERN = "log [that] %string% made [a] transaction [to [a] container] at %location%";

    private Expression<String> user;

    private Expression<Location> location;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        user = (Expression<String>)exprs[0];
        location = (Expression<Location>)exprs[1];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "log that " + user.toString(e, debug) + " made a transaction to a container at " +
                location.toString(e, debug);
    }

    @Override
    protected void execute(@NotNull Event e) {
        CondLoggingSucceeded.set(SkCoAPI.getCoreProtectAPI().logContainerTransaction(user.getSingle(e), location.getSingle(e)));
    }

}
