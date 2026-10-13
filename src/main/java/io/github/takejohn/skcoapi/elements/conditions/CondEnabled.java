package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("CoreProtect Is Enabled")
@Description("Checks if the server has the CoreProtect API enabled, and false if it does not.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class CondEnabled extends NegatableCondition {

    private static final String DISABLED_TAG = "disabled";

    public static final String PATTERN =
            "[the] CoreProtect API is[" + NEGATED_TAG + ":(n't| not)] (enabled|:" + DISABLED_TAG + ")";

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.CONDITION, SyntaxInfo.simple(CondEnabled.class, CondEnabled::new, PATTERN));
    }

    @Override
    protected boolean isNegativeCondition(int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        // "is not disabled" means "is enabled"
        return parseResult.hasTag(NEGATED_TAG) ^ parseResult.hasTag(DISABLED_TAG);
    }

    @Override
    protected boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                                      SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return isNegated() ? "CoreProtect API is disabled" : "CoreProtect API is enabled";
    }

    @Override
    public boolean checkBeforeNegated(@NotNull Event e) {
        return SkCoAPI.getCoreProtectAPI().isEnabled();
    }

}
