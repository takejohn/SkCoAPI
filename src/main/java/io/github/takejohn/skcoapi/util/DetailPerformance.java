package io.github.takejohn.skcoapi.util;

import ch.njol.skript.aliases.ItemData;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionList;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.UnparsedLiteral;
import ch.njol.skript.util.LiteralUtils;
import ch.njol.skript.util.Timespan;
import io.github.takejohn.skcoapi.SkCoAPI;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DetailPerformance {

    LOOKUP("lookup", CoreProtectAPI::performLookup),
    ROLLBACK("rollback", CoreProtectAPI::performRollback),
    RESTORE("restore", CoreProtectAPI::performRestore);

    /**
     * The expressions given to a lookup / rollback / restore syntax.
     */
    public static final class OptionExpressions {

        private final Expression<Timespan> time;

        private final Expression<String> restrictUsers;

        private final Expression<String> excludeUsers;

        private final Expression<Object> restrictBlocks;

        private final Expression<Object> excludeBlocks;

        private final Expression<Integer> actionList;

        private final Expression<Integer> radius;

        private final Expression<Location> radiusLocation;

        @SuppressWarnings("unchecked")
        private OptionExpressions(Expression<?> @NotNull [] exprs, @Nullable Expression<?> restrictBlocks,
                                  @Nullable Expression<?> excludeBlocks) {
            time = (Expression<Timespan>)exprs[0];
            restrictUsers = (Expression<String>)exprs[1];
            excludeUsers = (Expression<String>)exprs[2];
            this.restrictBlocks = (Expression<Object>)restrictBlocks;
            this.excludeBlocks = (Expression<Object>)excludeBlocks;
            actionList = (Expression<Integer>)exprs[5];
            radius = (Expression<Integer>)exprs[6];
            radiusLocation = (Expression<Location>)exprs[7];
        }

        /**
         * Creates option expressions from the expressions matched by {@link #syntaxPattern()}.
         *
         * @return null if a literal given to 'on' or 'except on' cannot be understood (an error has been printed)
         */
        public static @Nullable OptionExpressions of(Expression<?> @NotNull [] exprs) {
            final @Nullable Expression<?> restrictBlocks = convertBlockLiterals(exprs[3]);
            final @Nullable Expression<?> excludeBlocks = convertBlockLiterals(exprs[4]);
            if ((exprs[3] != null && restrictBlocks == null) || (exprs[4] != null && excludeBlocks == null)) {
                return null;
            }
            return new OptionExpressions(exprs, restrictBlocks, excludeBlocks);
        }

        /**
         * Skript leaves literals given to {@code %objects%} unparsed (e.g. {@code on stone}),
         * and they throw when evaluated. Parse them as one of the types CoreProtect can filter by.
         *
         * @return the converted expression, or null if a literal cannot be parsed
         */
        @SuppressWarnings("unchecked")
        private static @Nullable Expression<?> convertBlockLiterals(@Nullable Expression<?> expr) {
            if (expr instanceof UnparsedLiteral unparsed) {
                return unparsed.<Object>getConvertedExpression(ParseContext.DEFAULT,
                        ItemType.class, BlockData.class, EntityData.class);
            }
            if (expr instanceof ExpressionList<?> list && LiteralUtils.hasUnparsedLiteral(list)) {
                final Expression<?>[] elements = list.getExpressions();
                final Expression<?>[] converted = new Expression<?>[elements.length];
                for (int i = 0 ; i < elements.length ; i++) {
                    converted[i] = convertBlockLiterals(elements[i]);
                    if (converted[i] == null) {
                        return null;
                    }
                }
                return new ExpressionList<>(converted, Object.class, list.getAnd());
            }
            return expr;
        }

        /**
         * Evaluates all expressions.
         * Call this on the thread which owns the event (the main thread in most cases),
         * then the returned options can be passed to another thread safely.
         *
         * @return the evaluated options, or null if nothing can match: the time is not set, or
         * 'for', 'on', 'with action' or 'in radius' is given but evaluates to nothing (e.g. an unset variable).
         * CoreProtect treats an empty filter as no filter, which would make e.g. a rollback affect everyone.
         */
        public @Nullable Options evaluate(@NotNull Event e) {
            final @Nullable Timespan singleTime = time.getSingle(e);
            if (singleTime == null) {
                return null;
            }
            final @Nullable List<String> restrictUserList =
                    restrictUsers != null ? Arrays.asList(restrictUsers.getArray(e)) : null;
            final @Nullable List<Object> restrictBlockList =
                    restrictBlocks != null ? blocksAsList(restrictBlocks.getArray(e)) : null;
            final @Nullable List<Integer> actions = actionList != null ? Arrays.asList(actionList.getArray(e)) : null;
            final @Nullable Integer singleRadius = radius != null ? radius.getSingle(e) : null;
            if (isGivenButEmpty(restrictUserList) || isGivenButEmpty(restrictBlockList) || isGivenButEmpty(actions) ||
                    (radius != null && singleRadius == null)) {
                return null;
            }
            return new Options(
                    Timespans.toSeconds(singleTime),
                    restrictUserList,
                    excludeUsers != null ? Arrays.asList(excludeUsers.getArray(e)) : null,
                    restrictBlockList,
                    excludeBlocks != null ? blocksAsList(excludeBlocks.getArray(e)) : null,
                    actions,
                    singleRadius != null ? singleRadius : 0,
                    getRadiusLocation(e)
            );
        }

        private static boolean isGivenButEmpty(@Nullable List<?> filter) {
            return filter != null && filter.isEmpty();
        }

        private @Nullable Location getRadiusLocation(@NotNull Event e) {
            final Location singleLocation = radiusLocation != null ? radiusLocation.getSingle(e) : null;
            // Locations are mutable, so give CoreProtect its own copy.
            return singleLocation != null ? singleLocation.clone() : null;
        }

        private static @NotNull List<Object> blocksAsList(Object @NotNull [] blocks) {
            final List<Object> result = new ArrayList<>(blocks.length);
            for (Object block : blocks) {
                if (block instanceof EntityData<?> entityData) {
                    addIfNotNull(result, EntityTypes.fromEntityData(entityData));
                } else if (block instanceof ch.njol.skript.entity.EntityType entityType) {
                    addIfNotNull(result, EntityTypes.fromSkriptEntityType(entityType));
                } else if (block instanceof ItemType itemType) {
                    // An item type may stand for several materials (e.g. "wool"), so pass all of them.
                    for (ItemData itemData : itemType.getTypes()) {
                        addIfNotNull(result, itemData.getType());
                    }
                } else if (block instanceof BlockData blockData) {
                    result.add(blockData.getMaterial());
                } else if (block instanceof Entity entity) {
                    // e.g. 'on victim', or 'on zombie' which Skript reads as "the zombie" of the event
                    result.add(entity.getType());
                } else {
                    result.add(block);
                }
            }
            return result;
        }

        private static void addIfNotNull(@NotNull List<Object> list, @Nullable Object element) {
            if (element != null) {
                list.add(element);
            }
        }

    }

    /**
     * Evaluated arguments of a lookup / rollback / restore.
     */
    public record Options(int time,
                          @Nullable List<String> restrictUsers,
                          @Nullable List<String> excludeUsers,
                          @Nullable List<Object> restrictBlocks,
                          @Nullable List<Object> excludeBlocks,
                          @Nullable List<Integer> actionList,
                          int radius,
                          @Nullable Location radiusLocation) {
    }

    @FunctionalInterface
    public interface PerformFunction {

        List<String[]> perform(CoreProtectAPI api, int time, List<String> restrictUsers, List<String> excludeUsers,
                               List<Object> restrictBlocks, List<Object> excludeBlocks, List<Integer> actionList,
                               int radius, Location radiusLocation);

    }

    private final @NotNull String verb;

    private final @NotNull PerformFunction func;

    DetailPerformance(@NotNull String verb, @NotNull PerformFunction func) {
        this.verb = verb;
        this.func = func;
    }

    public @NotNull String syntaxPattern() {
        return verb + " [action] (data|logs) in [last] %timespan% [for %-strings%] [except for %-strings%]" +
                " [on %-objects%] [except on %-objects%]" +
                " [with ([an] action|actions) %-integers%] [in radius %-integer%]" +
                " [at %-location%]";
    }

    public @NotNull String toString(@NotNull OptionExpressions opt,
                                    @Nullable Event e, boolean debug) {
        return verb + " action logs in " + opt.time.toString(e, debug) +
                (opt.restrictUsers != null ? " for " + opt.restrictUsers.toString(e, debug) : "") +
                (opt.excludeUsers != null ? " except for " + opt.excludeUsers.toString(e, debug) : "") +
                (opt.restrictBlocks != null ? " on " + opt.restrictBlocks.toString(e, debug) : "") +
                (opt.excludeBlocks != null ? " except on " + opt.excludeBlocks.toString(e, debug) : "") +
                (opt.actionList != null ? " with action " + opt.actionList.toString(e, debug) : "") +
                (opt.radius != null ? " in radius " + opt.radius.toString(e, debug) : "") +
                (opt.radiusLocation != null ? " at " + opt.radiusLocation.toString(e, debug) : "");
    }

    /**
     * Calls CoreProtect API. This blocks until the database query finishes,
     * so call this off the main thread.
     */
    public @Nullable List<String[]> perform(@NotNull Options opt) {
        return func.perform(SkCoAPI.getCoreProtectAPI(), opt.time(), opt.restrictUsers(), opt.excludeUsers(),
                opt.restrictBlocks(), opt.excludeBlocks(), opt.actionList(), opt.radius(), opt.radiusLocation());
    }

}
