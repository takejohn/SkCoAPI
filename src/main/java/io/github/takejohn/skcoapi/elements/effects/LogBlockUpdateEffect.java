package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
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

    public @NotNull String toString(@org.eclipse.jdt.annotation.Nullable Event e, boolean debug, @NotNull String actionVerb) {
        return "log that " + user.toString(e, debug) + " " + actionVerb + " " + typeOrBlockData.toString(e, debug) +
                " at " + location.toString(e, debug);
    }

    public static void registerWithVerb(Class<? extends LogBlockUpdateEffect> c, @NotNull String actionVerb) {
        Skript.registerEffect(c, "log[ that] %string% " + actionVerb + " %material/blockdata% at %location%");
    }

    protected @NotNull Material getType(@NotNull Object typeOrBlockData) {
        if (typeOrBlockData instanceof Material) {
            return (Material)typeOrBlockData;
        } else {
            return ((BlockData)typeOrBlockData).getMaterial();
        }
    }

    protected static @Nullable BlockData getBlockData(@NotNull Object typeOrBlockData) {
        if (typeOrBlockData instanceof BlockData) {
            return (BlockData)typeOrBlockData;
        } else {
            return null;
        }
    }

}
