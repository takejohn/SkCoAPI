package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Has Placed/Built")
@Description("Checks if a user has already placed a block at the location within the specified time limit.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class CondPlaced extends ActedCondition {

    public static final String PATTERN =
            "%string% has[1¦(n't| not)] (placed|built) %block% in[ last] %timespan%[ (until|till) %-timespan%]";

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return user.toString(e, debug) + (isNegated() ? " has not placed " : " has placed ") +
                block.toString(e, debug) + " in " + time.toString(e, debug) +
                (offset != null ? " until " + offset.toString(e, debug) + " ago" : "");
    }

    @Override
    public boolean checkBeforeNegated(@NotNull Event e) {
        return SkCoAPI.coreProtectAPI.hasPlaced(getUser(e), getBlock(e), getTimeAsSeconds(e), getOffsetAsSeconds(e));
    }

}
