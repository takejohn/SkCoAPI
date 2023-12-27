package io.github.takejohn.skcoapi.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import io.github.takejohn.skcoapi.util.DetailPerformance;
import org.jetbrains.annotations.NotNull;

@Name("Lookup")
@Description({
        "Performs a lookup.",
        "This effect causes a delay of the script execution.",
        "Objects specified with 'on' and 'expect on' can be Entity Data, Entity Types, Item Types or Block Data.",
        "Use 'results' in this section to get the results of the lookup."
})
@Since("0.3.0")
@RequiredPlugins("CoreProtect")
@Examples({
        "lookup logs in a month in radius 5 at location of player:",
        "    set {_lookup::*} to results"
})
public class EffSecLookup extends EffSecPerform {

    private static final DetailPerformance LOOKUP = DetailPerformance.LOOKUP;

    public static void register() {
        Skript.registerSection(EffSecLookup.class, LOOKUP.syntaxPattern());
    }

    @Override
    protected @NotNull DetailPerformance performance() {
        return LOOKUP;
    }

}
