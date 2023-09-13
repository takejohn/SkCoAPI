package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import io.github.takejohn.skcoapi.elements.conditions.CondLoggingSucceeded;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Name("Log Placement")
@Description("Logs a block as being placed.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffLogPlacement extends LogBlockUpdateEffect {

    private static final String ACTION_VERB = "(placed|built)";

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return toString(e, debug, ACTION_VERB);
    }

    @Override
    protected void execute(@NotNull Event e) {
        final @NotNull Object singleTypeOrBlockData = Objects.requireNonNull(typeOrBlockData.getSingle(e));
        CondLoggingSucceeded.set(SkCoAPI.coreProtectAPI.logPlacement(user.getSingle(e), location.getSingle(e),
                getType(singleTypeOrBlockData), getBlockData(singleTypeOrBlockData)));
    }

    public static void register() {
        registerWithVerb(EffLogPlacement.class, ACTION_VERB);
    }

}
