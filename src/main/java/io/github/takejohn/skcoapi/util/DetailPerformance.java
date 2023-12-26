package io.github.takejohn.skcoapi.util;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Timespan;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum DetailPerformance {

    LOOKUP("lookup", SkCoAPI.coreProtectAPI::performLookup),
    ROLLBACK("rollback", SkCoAPI.coreProtectAPI::performRollback),
    RESTORE("restore", SkCoAPI.coreProtectAPI::performRestore);

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
        public OptionExpressions(Expression<?> @NotNull [] exprs) {
            time = (Expression<Timespan>)exprs[0];
            restrictUsers = (Expression<String>)exprs[1];
            excludeUsers = (Expression<String>)exprs[2];
            restrictBlocks = (Expression<Object>)exprs[3];
            excludeBlocks = (Expression<Object>)exprs[4];
            actionList = (Expression<Integer>)exprs[5];
            radius = (Expression<Integer>)exprs[6];
            radiusLocation = (Expression<Location>)exprs[7];
        }

        public int getTime(@NotNull Event e) {
            return Timespans.toSeconds(Objects.requireNonNull(time.getSingle(e)));
        }

        public @Nullable List<String> getRestrictUsers(@NotNull Event e) {
            return restrictUsers != null ? Arrays.asList(restrictUsers.getArray(e)) : null;
        }

        @Nullable
        public List<String> getExcludeUsers(@NotNull Event e) {
            return excludeUsers != null ? Arrays.asList(excludeUsers.getArray(e)) : null;
        }

        @Nullable
        public List<Object> getRestrictBlocks(@NotNull Event e) {
            return restrictBlocks != null ? blocksAsList(restrictBlocks.getArray(e)) : null;
        }

        public @Nullable List<Object> getExcludeBlock(@NotNull Event e) {
            return excludeBlocks != null ? blocksAsList(excludeBlocks.getArray(e)) : null;
        }

        public @Nullable List<Integer> getActionList(@NotNull Event e) {
            return actionList != null ? Arrays.asList(actionList.getArray(e)) : null;
        }

        public int getRadius(@NotNull Event e) {
            final Integer singleRadius = radius != null ? radius.getSingle(e) : null;
            return singleRadius != null ? singleRadius : 0;
        }

        public @Nullable Location getRadiusLocation(@NotNull Event e) {
            return radiusLocation != null ? radiusLocation.getSingle(e) : null;
        }

        private @NotNull List<Object> blocksAsList(Object @NotNull [] blocks) {
            final List<Object> result = new ArrayList<>(blocks.length);
            for (Object block : blocks) {
                if (block instanceof EntityData<?>) {
                    result.add(EntityTypes.fromEntityData((EntityData<?>)block));
                } else if (block instanceof ch.njol.skript.entity.EntityType) {
                    result.add(EntityTypes.fromSkriptEntityType((ch.njol.skript.entity.EntityType)block));
                } else if (block instanceof ItemType) {
                    result.add(((ItemType)block).getMaterial());
                } else if (block instanceof BlockData) {
                    result.add(((BlockData)block).getMaterial());
                } else {
                    result.add(block);
                }
            }
            return result;
        }

    }

    @FunctionalInterface
    public interface PerformFunction {

        List<String[]> perform(int time, List<String> restrictUsers, List<String> excludeUsers,
                               List<Object> restrictBlocks, List<Object> excludeBlocks, List<Integer> actionList,
                               int radius, Location radiusLocation);

    }

    private final @NotNull String verb;

    private final @NotNull DetailPerformance.PerformFunction func;

    DetailPerformance(@NotNull String verb, @NotNull DetailPerformance.PerformFunction func) {
        this.verb = verb;
        this.func = func;
    }

    public @NotNull String syntaxPattern() {
        return verb + "[ action] (data|logs) in[ last] %timespan%[ for %-strings%][ except for %-strings%]" +
                "[ on %-objects%][ except on %-objects%]" +
                "[ with ([an ]action|actions) %-integers%][ in radius %-integer%]" +
                "[ at %-location%]";
    }

    public @NotNull String toString(@NotNull OptionExpressions opt,
                                    @org.jetbrains.annotations.Nullable Event e, boolean debug) {
        return verb + " action logs in " + opt.time.toString(e, debug) +
                (opt.restrictUsers != null ? " for " + opt.restrictUsers.toString(e, debug) : "") +
                (opt.excludeUsers != null ? " except for " + opt.excludeUsers.toString(e, debug) : "") +
                (opt.restrictBlocks != null ? " on " + opt.restrictBlocks.toString(e, debug) : "") +
                (opt.excludeBlocks != null ? " except on " + opt.excludeBlocks.toString(e, debug) : "") +
                (opt.actionList != null ? " with action " + opt.actionList.toString(e, debug) : "") +
                (opt.radius != null ? " in radius " + opt.radius.toString(e, debug) : "") +
                (opt.radiusLocation != null ? " at " + opt.radiusLocation.toString(e, debug) : "");
    }

    public List<String[]> perform(@NotNull OptionExpressions opt, @NotNull Event e) {
        return func.perform(opt.getTime(e), opt.getRestrictUsers(e), opt.getExcludeUsers(e),
                opt.getRestrictBlocks(e), opt.getExcludeBlock(e), opt.getActionList(e),
                opt.getRadius(e), opt.getRadiusLocation(e));
    }

}
