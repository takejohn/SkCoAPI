package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.Skript;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Name("Has Placed/Removed")
@Description("Checks if a user has already placed or removed a block at the location within the specified time limit.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class CondPlacedRemoved extends NegatableCondition {

    private enum ActionPredicate {

        PLACED("placed") {

            @Override
            public boolean test(@NotNull String user, @Nullable Block block, int time, int offset) {
                return SkCoAPI.getCoreProtectAPI().hasPlaced(user, block, time, offset);
            }

        },
        REMOVED("broken") {

            @Override
            public boolean test(@NotNull String user, @Nullable Block block, int time, int offset) {
                return SkCoAPI.getCoreProtectAPI().hasRemoved(user, block, time, offset);
            }

        };

        private final @NotNull String string;

        ActionPredicate(@NotNull String string) {
            this.string = string;
        }

        @Override
        public @NotNull String toString() {
            return string;
        }

        public abstract boolean test(@NotNull String user, @Nullable Block block, int time, int offset);

    }

    private static final String PATTERN =
            "%string% has[1¦(n't| not)] (placed|built|2¦broken|2¦removed) %block% in [last] %timespan% [(until|till) %-timespan%]";

    private ActionPredicate actionPredicate;

    protected Expression<String> user;

    protected Expression<Block> block;

    protected Expression<Timespan> time;

    protected Expression<Timespan> offset;

    @Override
    protected boolean isNegativeCondition(int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        return (parseResult.mark & 1) != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                                   SkriptParser.@NotNull ParseResult parseResult) {
        actionPredicate = (parseResult.mark & 2) == 0 ? ActionPredicate.PLACED : ActionPredicate.REMOVED;
        user = (Expression<String>)exprs[0];
        block = (Expression<Block>)exprs[1];
        time = (Expression<Timespan>)exprs[2];
        offset = (Expression<Timespan>)exprs[3];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return user.toString(e, debug) + (isNegated() ? " has not " : " has ") + actionPredicate + " " +
                block.toString(e, debug) + " in " + time.toString(e, debug) +
                (offset != null ? " until " + offset.toString(e, debug) + " ago" : "");
    }

    @Override
    public boolean checkBeforeNegated(@NotNull Event e) {
        return actionPredicate.test(getUser(e), getBlock(e), getTimeAsSeconds(e), getOffsetAsSeconds(e));
    }

    public static void register() {
        Skript.registerCondition(CondPlacedRemoved.class, PATTERN);
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
