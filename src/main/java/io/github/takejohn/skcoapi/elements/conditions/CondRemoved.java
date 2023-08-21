package io.github.takejohn.skcoapi.elements.conditions;

import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class CondRemoved extends ActedCondition {

    public static final String PATTERN =
            "%string% has[1¦(n't| not)] (broken|removed) %block% in[ last] %timespan%[ (until|till) %-timespan%]";

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return user.toString(e, debug) + (isNegated() ? " has not broken " : " has broken ") +
                block.toString(e, debug) + " in " + time.toString(e, debug) +
                (offset != null ? " until " + offset.toString(e, debug) + " ago" : "");
    }

    @Override
    public boolean checkBeforeNegated(@NotNull Event e) {
        return SkCoAPI.coreProtectAPI.hasRemoved(getUser(e), getBlock(e), getTimeAsSeconds(e), getOffsetAsSeconds(e));
    }

}
