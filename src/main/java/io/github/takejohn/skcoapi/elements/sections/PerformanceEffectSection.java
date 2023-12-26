package io.github.takejohn.skcoapi.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.*;
import ch.njol.skript.timings.SkriptTimings;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.util.DetailPerformance;
import io.github.takejohn.skcoapi.util.ParseResults;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public abstract class PerformanceEffectSection extends EffectSection {

    private DetailPerformance.OptionExpressions opt;

    private Trigger innerTrigger;

    protected abstract @NotNull DetailPerformance performance();

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult, @Nullable SectionNode sectionNode,
                        @Nullable List<TriggerItem> triggerItems) {
        getParser().setHasDelayBefore(Kleenean.TRUE);
        opt = new DetailPerformance.OptionExpressions(exprs);
        if (sectionNode != null) {
            innerTrigger = loadCode(sectionNode, CoreProtectPerformanceCompleteEvent.NAME, CoreProtectPerformanceCompleteEvent.class);
        }
        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(@NotNull Event e) {
        debug(e, true);
        Delay.addDelayedEvent(e);
        final Object localVariables = Variables.removeLocals(e);
        if (Skript.getInstance().isEnabled()) {
            SkCoAPI.runTaskAsynchronously(() -> asynchronousTask(e, localVariables));
        }
        return null;
    }

    protected @NotNull Event execute(@NotNull Event formerEvent) {
        return new CoreProtectPerformanceCompleteEvent(
                ParseResults.parseResults(performance().perform(opt, formerEvent)));
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return performance().toString(opt, e, debug);
    }

    private void asynchronousTask(@NotNull Event e, @Nullable Object localVariables) {
        Variables.setLocalVariables(e, localVariables);
        final @NotNull Event laterEvent = execute(e);
        if (getNext() != null) {
            SkCoAPI.runTask(() -> synchronouslyNext(e, laterEvent));
        } else {
            Variables.removeLocals(e);
        }
    }

    private void synchronouslyNext(@NotNull Event formerEvent, @NotNull Event laterEvent) {
        final Object timing = startTiming();
        if (innerTrigger != null) {
            final Object innerLocalVariables = Variables.copyLocalVariables(formerEvent);
            Variables.setLocalVariables(laterEvent, innerLocalVariables);
            TriggerItem.walk(innerTrigger, laterEvent);
            Variables.setLocalVariables(formerEvent, Variables.removeLocals(laterEvent));
        }
        TriggerItem.walk(Objects.requireNonNull(getNext()), formerEvent);
        Variables.removeLocals(formerEvent);
        SkriptTimings.stop(timing);
    }

    private @Nullable Object startTiming() {
        if (!SkriptTimings.enabled()) {
            return null;
        }
        final Trigger trigger = getTrigger();
        if (trigger == null) {
            return null;
        }
        return SkriptTimings.start(trigger.getDebugLabel());
    }

}
