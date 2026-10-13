package io.github.takejohn.skcoapi.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.util.DetailPerformance;
import io.github.takejohn.skcoapi.util.ParseResults;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * Runs a lookup / rollback / restore asynchronously, then runs the section body with the results,
 * and finally continues the trigger on the main thread.
 */
public abstract class PerformanceEffectSection extends EffectSection {

    private DetailPerformance.OptionExpressions opt;

    private @Nullable Trigger innerTrigger;

    protected abstract @NotNull DetailPerformance performance();

    protected static <E extends PerformanceEffectSection> void register(@NotNull SyntaxRegistry registry,
                                                                        @NotNull Class<E> c,
                                                                        @NotNull Supplier<E> supplier,
                                                                        @NotNull DetailPerformance performance) {
        registry.register(SyntaxRegistry.SECTION, SyntaxInfo.simple(c, supplier, performance.syntaxPattern()));
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult, @Nullable SectionNode sectionNode,
                        @Nullable List<TriggerItem> triggerItems) {
        final DetailPerformance.@Nullable OptionExpressions options = DetailPerformance.OptionExpressions.of(exprs);
        if (options == null) {
            return false;
        }
        opt = options;
        if (sectionNode != null) {
            innerTrigger = loadCode(sectionNode, CoreProtectPerformanceCompleteEvent.NAME,
                    CoreProtectPerformanceCompleteEvent.class);
        }
        // The code after this section runs once CoreProtect has finished.
        getParser().setHasDelayBefore(Kleenean.TRUE);
        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(@NotNull Event e) {
        debug(e, true);

        if (!Skript.getInstance().isEnabled()) {
            return null;
        }

        // Skript expressions are not thread-safe, so evaluate them here on the main thread.
        final DetailPerformance.@Nullable Options options = opt.evaluate(e);
        if (options == null) {
            // Nothing can match, so do not let CoreProtect run without the filters the script asked for.
            runInnerTrigger(e, ParseResults.EMPTY);
            return getNext();
        }

        Delay.addDelayedEvent(e);
        final @Nullable Object localVariables = Variables.removeLocals(e);
        SkCoAPI.runTaskAsynchronously(() -> {
            final CoreProtectAPI.ParseResult[] results = performSafely(options);
            SkCoAPI.runTask(() -> resume(e, localVariables, results));
        });
        return null;
    }

    private CoreProtectAPI.@NotNull ParseResult @NotNull[] performSafely(DetailPerformance.@NotNull Options options) {
        try {
            return ParseResults.parseResults(performance().perform(options));
        } catch (RuntimeException ex) {
            // Do not let the trigger hang forever just because CoreProtect failed.
            SkCoAPI.getPlugin().getLogger().log(Level.SEVERE,
                    "CoreProtect " + performance().name().toLowerCase(Locale.ROOT) + " failed", ex);
            return ParseResults.EMPTY;
        }
    }

    private void resume(@NotNull Event e, @Nullable Object localVariables,
                        CoreProtectAPI.@NotNull ParseResult @NotNull[] results) {
        if (localVariables != null) {
            Variables.setLocalVariables(e, localVariables);
        }
        runInnerTrigger(e, results);
        final @Nullable TriggerItem next = getNext();
        if (next != null) {
            TriggerItem.walk(next, e);
        }
        Variables.removeLocals(e);
    }

    /**
     * Runs the section body, sharing the local variables of the outer trigger.
     */
    private void runInnerTrigger(@NotNull Event e, CoreProtectAPI.@NotNull ParseResult @NotNull[] results) {
        if (innerTrigger == null) {
            return;
        }
        final Trigger trigger = innerTrigger;
        final CoreProtectPerformanceCompleteEvent completeEvent = new CoreProtectPerformanceCompleteEvent(results);
        Variables.withLocalVariables(e, completeEvent, () -> TriggerItem.walk(trigger, completeEvent));
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return performance().toString(opt, e, debug);
    }

}
