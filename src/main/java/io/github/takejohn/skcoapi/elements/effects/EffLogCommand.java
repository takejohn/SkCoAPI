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
import io.github.takejohn.skcoapi.elements.conditions.CondLoggingSucceeded;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Log Executed")
@Description("Logs a command as having been executed by a player.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffLogCommand extends Effect {

    public static final String PATTERN = "log [that] %player% executed [[the] command] %string%";

    private Expression<Player> player;

    private Expression<String> command;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT, SyntaxInfo.simple(EffLogCommand.class, EffLogCommand::new, PATTERN));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        player = (Expression<Player>)exprs[0];
        command = (Expression<String>)exprs[1];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "log that " + player.toString(e, debug) + " executed " + command.toString(e, debug);
    }

    @Override
    protected void execute(@NotNull Event e) {
        final Player singlePlayer = player.getSingle(e);
        final String singleCommand = command.getSingle(e);
        CondLoggingSucceeded.set(singlePlayer != null && singleCommand != null &&
                SkCoAPI.getCoreProtectAPI().logCommand(singlePlayer, singleCommand));
    }

}
