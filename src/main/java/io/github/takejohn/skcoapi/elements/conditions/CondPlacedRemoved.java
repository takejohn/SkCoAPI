package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.util.Timespans;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class CondPlacedRemoved extends NegatableCondition {

    protected Expression<String> user;

    protected Expression<Block> block;

    protected Expression<Timespan> time;

    protected Expression<Timespan> offset;

    @SuppressWarnings("unchecked")
    @Override
    public boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                                   SkriptParser.@NotNull ParseResult parseResult) {
        user = (Expression<String>)exprs[0];
        block = (Expression<Block>)exprs[1];
        time = (Expression<Timespan>)exprs[2];
        offset = (Expression<Timespan>)exprs[3];
        return true;
    }

    protected String getUser(Event e) {
        return user.getSingle(e);
    }

    protected Block getBlock(Event e) {
        return block.getSingle(e);
    }

    protected int getTimeAsSeconds(Event e) {
        return Timespans.toSeconds(Objects.requireNonNull(time.getSingle(e)));
    }

    protected int getOffsetAsSeconds(Event e) {
        return offset != null ? Timespans.toSeconds(Objects.requireNonNull(offset.getSingle(e))) : 0;
    }

}
