package io.github.takejohn.skcoapi.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("CoreProtect Where")
@Description({
        "A section to specify options for a lookup/rollback/restore."
})
@Since("0.4.0")
@RequiredPlugins("CoreProtect")
/*
@Examples({
        "command /lookup:",
        "    executable by: players",
        "    trigger:",
        "        set {_loc} to location of player",
        "        CoreProtect where:",
        "            set time to a month",
        "            set radius to 5",
        "            set radius location to {_loc}",
        "        perform lookup:",
        "            set {_lookup::*} to results",
        "        send {_lookup::*}"
})
*/
public class SecCoreProtectWhere extends Section {

    private static final @NotNull String PATTERN = "CoreProtect where";

    private Trigger trigger;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult, @NotNull SectionNode sectionNode,
                        @NotNull List<TriggerItem> triggerItems) {
        trigger = loadCode(sectionNode, CoreProtectPerformanceOptionEvent.NAME, CoreProtectPerformanceOptionEvent.class);
        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(@NotNull Event e) {
        debug(e, true);
        Object localVariables = Variables.copyLocalVariables(e);
        final CoreProtectPerformanceOptionEvent triggerEvent = new CoreProtectPerformanceOptionEvent();
        Variables.setLocalVariables(triggerEvent, localVariables);
        trigger.execute(triggerEvent);
        return super.walk(e, false);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return PATTERN;
    }

    public static void register() {
        Skript.registerSection(SecCoreProtectWhere.class, PATTERN);
    }

}
