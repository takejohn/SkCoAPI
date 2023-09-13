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

@Name("Lookup")
@Description({
        "Performs a lookup.",
        "Objects specified with `on` and `expect on` can be Entity Types, Item Types or Block Data."
})
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprLookup extends DetailPerformExpression {

    public static final String VERB = "lookup";

    @Override
    public @NotNull String toString(@org.eclipse.jdt.annotation.Nullable Event e, boolean debug) {
        return toString(e, debug, VERB);
    }

    @Override
    protected @Nullable List<String[]> perform(@NotNull Event e) {
        return SkCoAPI.coreProtectAPI.performLookup(timeAsSeconds(e), restrictUserList(e), excludeUserList(e),
                restrictBlockList(e), excludeBlockList(e), getActionList(e), getRadius(e), getRadiusLocation(e));
    }

    public static void register() {
        registerWithVerb(ExprLookup.class, VERB);
    }

}
