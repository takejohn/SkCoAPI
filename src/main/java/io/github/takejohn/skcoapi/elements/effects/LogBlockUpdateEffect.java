package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.elements.conditions.CondLoggingSucceeded;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class LogBlockUpdateEffect extends Effect {

    protected Expression<String> user;

    protected Expression<Object> typeOrBlockData;

    protected Expression<Location> location;

    /**
     * Builds the pattern of an effect which logs a block update.
     *
     * @param actionVerb the verbs in the past tense, e.g. {@code (placed|built)}
     */
    protected static @NotNull String patternWithVerb(@NotNull String actionVerb) {
        return "log [that] %string% " + actionVerb + " %material/blockdata% at %location%";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        user = (Expression<String>)exprs[0];
        typeOrBlockData = (Expression<Object>)exprs[1];
        location = (Expression<Location>)exprs[2];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "log that " + user.toString(e, debug) + " " + actionVerb() + " " + typeOrBlockData.toString(e, debug) +
                " at " + location.toString(e, debug);
    }

    protected abstract @NotNull String actionVerb();

    @Override
    protected void execute(@NotNull Event e) {
        final @Nullable String singleUser = user.getSingle(e);
        final @Nullable Object singleTypeOrBlockData = typeOrBlockData.getSingle(e);
        final @Nullable Location singleLocation = location.getSingle(e);
        if (singleUser == null || singleTypeOrBlockData == null || singleLocation == null) {
            CondLoggingSucceeded.set(false);
            return;
        }
        CondLoggingSucceeded.set(log(singleUser, singleLocation, getType(singleTypeOrBlockData),
                getBlockData(singleTypeOrBlockData)));
    }

    protected abstract boolean log(@NotNull String user, @NotNull Location location, @NotNull Material type,
                                   @Nullable BlockData blockData);

    private static @NotNull Material getType(@NotNull Object typeOrBlockData) {
        if (typeOrBlockData instanceof Material material) {
            return material;
        } else {
            return ((BlockData)typeOrBlockData).getMaterial();
        }
    }

    private static @Nullable BlockData getBlockData(@NotNull Object typeOrBlockData) {
        if (typeOrBlockData instanceof BlockData blockData) {
            return blockData;
        } else {
            return null;
        }
    }

}
