package io.github.takejohn.skcoapi.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.util.DetailPerformance;
import org.jetbrains.annotations.NotNull;

@Name("Lookup")
@Description({
        "Performs a lookup.",
        "This effect causes a delay of the script execution.",
        "Objects specified with `on` and `expect on` can be Entity Data, Entity Types, Item Types or Block Data."
})
@Since("0.2.1, 0.3.0 (effect section)")
@RequiredPlugins("CoreProtect")
public class EffSecLookup extends PerformanceEffectSection {

    private static final DetailPerformance LOOKUP = DetailPerformance.LOOKUP;

    public static void register() {
        Skript.registerSection(EffSecLookup.class, LOOKUP.syntaxPattern());
    }

    @Override
    protected @NotNull DetailPerformance performance() {
        return LOOKUP;
    }

}
