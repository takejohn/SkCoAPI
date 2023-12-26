package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.Skript;
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

    protected abstract String actionVerb();

    protected static void registerWithVerb(Class<? extends LogBlockUpdateEffect> c, @NotNull String actionVerb) {
        Skript.registerEffect(c, "log[ that] %string% " + actionVerb + " %material/blockdata% at %location%");
    }

    @Override
    protected void execute(@NotNull Event e) {
        final @Nullable Object singleTypeOrBlockData = typeOrBlockData.getSingle(e);
        if (singleTypeOrBlockData != null) {
            CondLoggingSucceeded.set(log(user.getSingle(e), location.getSingle(e), getType(singleTypeOrBlockData),
                    getBlockData(singleTypeOrBlockData)));
        } else {
            CondLoggingSucceeded.set(false);
        }
    }

    protected abstract boolean log(String user, Location location, Material type, BlockData blockData);

    private @NotNull Material getType(@NotNull Object typeOrBlockData) {
        if (typeOrBlockData instanceof Material) {
            return (Material)typeOrBlockData;
        } else {
            return ((BlockData)typeOrBlockData).getMaterial();
        }
    }

    private static @Nullable BlockData getBlockData(@NotNull Object typeOrBlockData) {
        if (typeOrBlockData instanceof BlockData) {
            return (BlockData)typeOrBlockData;
        } else {
            return null;
        }
    }

}
