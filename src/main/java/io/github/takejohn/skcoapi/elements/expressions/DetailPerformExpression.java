package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.util.EntityTypes;
import io.github.takejohn.skcoapi.util.Timespans;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class DetailPerformExpression extends PerformExpression {

    protected Expression<Timespan> time;

    protected Expression<String> restrictUsers;

    protected Expression<String> excludeUsers;

    protected Expression<Object> restrictBlocks;

    protected Expression<Object> excludeBlocks;

    protected Expression<Integer> actionList;

    protected Expression<Integer> radius;

    protected Expression<Location> radiusLocation;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        time = (Expression<Timespan>)exprs[0];
        restrictUsers = (Expression<String>)exprs[1];
        excludeUsers = (Expression<String>)exprs[2];
        restrictBlocks = (Expression<Object>)exprs[3];
        excludeBlocks = (Expression<Object>)exprs[4];
        actionList = (Expression<Integer>)exprs[5];
        radius = (Expression<Integer>)exprs[6];
        radiusLocation = (Expression<Location>)exprs[7];
        return true;
    }

    private static @NotNull String pattern(@NotNull String perform) {
        return perform + "[ action] (data|logs) in[ last] %timespan%[ for %-strings%][ except for %-strings%]" +
                "[ on %-objects%][ except on %-objects%]" +
                "[ with ([an ]action|actions) %-integers%][ in radius %-integer%]" +
                "[ at %-location%]";
    }

    protected @NotNull String toString(@org.eclipse.jdt.annotation.Nullable Event e, boolean debug, @NotNull String verb) {
        return verb + " action logs in " + time.toString(e, debug) +
                (restrictUsers != null ? " for " + restrictUsers.toString(e, debug) : "") +
                (excludeUsers != null ? " except for " + excludeUsers.toString(e, debug) : "") +
                (restrictBlocks != null ? " on " + restrictBlocks.toString(e, debug) : "") +
                (excludeBlocks != null ? " except on " + excludeBlocks.toString(e, debug) : "") +
                (actionList != null ? " with action " + actionList.toString(e, debug) : "") +
                (radius != null ? " in radius " + radius.toString(e, debug) : "") +
                (radiusLocation != null ? " at " + radiusLocation.toString(e, debug) : "");
    }

    protected int timeAsSeconds(@NotNull Event e) {
        return Timespans.toSeconds(Objects.requireNonNull(time.getSingle(e)));
    }

    protected @Nullable List<String> restrictUserList(@NotNull Event e) {
        return restrictUsers != null ? Arrays.asList(restrictUsers.getArray(e)) : null;
    }

    protected @Nullable List<String> excludeUserList(@NotNull Event e) {
        return excludeUsers != null ? Arrays.asList(excludeUsers.getArray(e)) : null;
    }

    protected @Nullable List<Object> restrictBlockList(@NotNull Event e) {
        return restrictBlocks != null ? blocksAsList(restrictBlocks.getArray(e)) : null;
    }

    protected @Nullable List<Object> excludeBlockList(@NotNull Event e) {
        return excludeBlocks != null ? blocksAsList(excludeBlocks.getArray(e)) : null;
    }

    private @NotNull List<Object> blocksAsList(Object[] blocks) {
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

    protected @Nullable List<Integer> getActionList(@NotNull Event e) {
        return actionList != null ? Arrays.asList(actionList.getArray(e)) : null;
    }

    protected int getRadius(@NotNull Event e) {
        final Integer singleRadius = radius != null ? radius.getSingle(e) : null;
        return singleRadius != null ? singleRadius : 0;
    }

    protected @Nullable Location getRadiusLocation(@NotNull Event e) {
        return radiusLocation != null ? radiusLocation.getSingle(e) : null;
    }

    public static void registerWithVerb(@NotNull Class<? extends DetailPerformExpression>c, @NotNull String verb) {
        Skript.registerExpression(c, CoreProtectAPI.ParseResult.class, ExpressionType.COMBINED, pattern(verb));
    }

}
