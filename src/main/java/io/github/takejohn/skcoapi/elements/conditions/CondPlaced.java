package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Has Placed/Built")
@Description("Checks if a user has already placed a block at the location within the specified time limit.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class CondPlaced extends ActedCondition {

    public static final String PATTERN = pattern("(placed|built)");

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.CONDITION, SyntaxInfo.simple(CondPlaced.class, CondPlaced::new, PATTERN));
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return user.toString(e, debug) + (isNegated() ? " has not placed " : " has placed ") +
                block.toString(e, debug) + " in " + time.toString(e, debug) +
                (offset != null ? " until " + offset.toString(e, debug) + " ago" : "");
    }

    @Override
    protected boolean hasActed(@NotNull String user, @NotNull Block block, int time, int offset) {
        return SkCoAPI.getCoreProtectAPI().hasPlaced(user, block, time, offset);
    }

}
