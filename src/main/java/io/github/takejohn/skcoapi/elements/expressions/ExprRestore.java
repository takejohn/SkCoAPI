package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("Restore")
@Description({
        "Performs a restore.",
        "Objects specified with `on` and `expect on` can be Entity Data, Entity Types, Item Types or Block Data."
})
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprRestore extends DetailPerformExpression {

    public static final String VERB = "restore";

    @Override
    public @NotNull String toString(@org.eclipse.jdt.annotation.Nullable Event e, boolean debug) {
        return toString(e, debug, VERB);
    }

    @Override
    protected @Nullable List<String[]> perform(@NotNull Event e) {
        return SkCoAPI.coreProtectAPI.performRestore(timeAsSeconds(e), restrictUserList(e), excludeUserList(e),
                restrictBlockList(e), excludeBlockList(e), getActionList(e), getRadius(e), getRadiusLocation(e));
    }

    public static void register() {
        registerWithVerb(ExprRestore.class, VERB);
    }

}
