package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.util.Timespans;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ActedCondition extends NegatableCondition {

    protected Expression<String> user;

    protected Expression<Block> block;

    protected Expression<Timespan> time;

    protected Expression<Timespan> offset;

    /**
     * Builds the pattern of a condition which checks a user has acted on a block.
     *
     * @param verbs the verbs in the past participle, e.g. {@code (placed|built)}
     */
    protected static @NotNull String pattern(@NotNull String verbs) {
        return "%string% has[" + NEGATED_TAG + ":(n't| not)] " + verbs +
                " %block% in [last] %timespan% [(until|till) %-timespan%]";
    }

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

    @Override
    protected final boolean checkBeforeNegated(@NotNull Event e) {
        final @Nullable String singleUser = user.getSingle(e);
        final @Nullable Block singleBlock = block.getSingle(e);
        final @Nullable Timespan singleTime = time.getSingle(e);
        if (singleUser == null || singleBlock == null || singleTime == null) {
            return false;
        }
        final @Nullable Timespan singleOffset = offset != null ? offset.getSingle(e) : null;
        return hasActed(singleUser, singleBlock, Timespans.toSeconds(singleTime),
                singleOffset != null ? Timespans.toSeconds(singleOffset) : 0);
    }

    protected abstract boolean hasActed(@NotNull String user, @NotNull Block block, int time, int offset);

}
