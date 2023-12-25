package io.github.takejohn.skcoapi.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.util.DetailPerformance;
import org.jetbrains.annotations.NotNull;

@Name("Rollback")
@Description({
        "Performs a rollback.",
        "This effect causes a delay of the script execution.",
        "Objects specified with `on` and `expect on` can be Entity Types, Item Types or Block Data."
})
@Since("0.2.1")
@RequiredPlugins("CoreProtect")
public class EffSecRollback extends PerformanceEffectSection {

    private static final DetailPerformance ROLLBACK = DetailPerformance.ROLLBACK;

    public static void register() {
        Skript.registerSection(EffSecRollback.class, ROLLBACK.syntaxPattern());
    }

    @Override
    protected @NotNull DetailPerformance performance() {
        return ROLLBACK;
    }


}
