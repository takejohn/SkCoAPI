package io.github.takejohn.skcoapi.elements.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.elements.types.CoreProtectLogs;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Is Rolled Back")
@Description("Checks If the block is currently rolled back or not.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class CondRolledBack extends NegatableCondition {

    public static final String PATTERN =
            "%" + CoreProtectLogs.CODE_NAME + "% is[" + NEGATED_TAG + ":(n't| not)] rolled back";

    private Expression<CoreProtectAPI.ParseResult> log;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.CONDITION,
                SyntaxInfo.simple(CondRolledBack.class, CondRolledBack::new, PATTERN));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean afterSetNegated(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                                      SkriptParser.@NotNull ParseResult parseResult) {
        log = (Expression<CoreProtectAPI.ParseResult>)exprs[0];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return log.toString(e, debug) + (isNegated() ? " is not rolled back" : " is rolled back");
    }

    @Override
    public boolean checkBeforeNegated(@NotNull Event e) {
        final CoreProtectAPI.ParseResult singleLog = log.getSingle(e);
        if (singleLog == null) {
            return false;
        }
        return singleLog.isRolledBack();
    }

}
