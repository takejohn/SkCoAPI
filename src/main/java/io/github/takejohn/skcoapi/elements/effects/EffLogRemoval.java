package io.github.takejohn.skcoapi.elements.effects;

import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EffLogRemoval extends LogBlockUpdateEffect {

    private static final String ACTION_VERB = "(broke|removed)";

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return toString(e, debug, ACTION_VERB);
    }

    @Override
    protected void execute(@NotNull Event e) {
        final @NotNull Object singleTypeOrBlockData = Objects.requireNonNull(typeOrBlockData.getSingle(e));
        SkCoAPI.coreProtectAPI.logRemoval(user.getSingle(e), location.getSingle(e),
                getType(singleTypeOrBlockData), getBlockData(singleTypeOrBlockData));
    }

    public static void register() {
        registerWithVerb(EffLogRemoval.class, ACTION_VERB);
    }

}
