package io.github.takejohn.skcoapi.elements.expressions;

import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExprRollback extends DetailPerformExpression {

    public static final String VERB = "rollback";

    @Override
    public @NotNull String toString(@org.eclipse.jdt.annotation.Nullable Event e, boolean debug) {
        return toString(e, debug, VERB);
    }

    @Override
    protected @Nullable List<String[]> perform(@NotNull Event e) {
        return SkCoAPI.coreProtectAPI.performRollback(timeAsSeconds(e), restrictUserList(e), excludeUserList(e),
                restrictBlockList(e), excludeBlockList(e), getActionList(e), getRadius(e), getRadiusLocation(e));
    }

    public static void register() {
        registerWithVerb(ExprRollback.class, VERB);
    }

}
