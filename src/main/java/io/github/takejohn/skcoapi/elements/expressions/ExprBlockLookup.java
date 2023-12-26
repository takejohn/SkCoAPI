package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.util.Timespans;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@Name("Lookup On Block")
@Description("Performs a full lookup on a single block.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprBlockLookup extends PerformExpression {

    public static final String PATTERN = "lookup[ action] (data|logs) on %block% in[ last] %timespan%";

    private Expression<Block> block;

    private Expression<Timespan> time;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        block = (Expression<Block>)exprs[0];
        time = (Expression<Timespan>)exprs[1];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "lookup action logs on " + block.toString(e, debug) + " in " + time.toString(e, debug);
    }

    @Override
    protected @Nullable List<String[]> perform(@NotNull Event e) {
        final Block singleBlock = block.getSingle(e);
        final int timeInSeconds = Timespans.toSeconds(Objects.requireNonNull(time.getSingle(e)));
        return  SkCoAPI.coreProtectAPI.blockLookup(singleBlock, timeInSeconds);
    }

    public static void register() {
        register(ExprBlockLookup.class, PATTERN);
    }

}
