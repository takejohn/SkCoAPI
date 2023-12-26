package io.github.takejohn.skcoapi.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.coreprotect.event.CoreProtectPreLogEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class EvtPreLog extends SkriptEvent {

    public static final String PATTERN = "CoreProtect log[ging] [(for %-strings%|[an] action [by %-strings%])]";

    @Nullable Literal<String> users;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        users = (Literal<String>)args[0];
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        if (users == null) {
            return true;
        }

        final String actualUser = ((CoreProtectPreLogEvent)e).getUser();
        return users.check(e, o -> o.equals(actualUser));
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        if (users == null) {
            return "on CoreProtect logging";
        } else {
            return "on CoreProtect logging for " + users.toString(e, debug);
        }
    }

    public static void register() {
        Skript.registerEvent("Logging Action",
                EvtPreLog.class, CoreProtectPreLogEvent.class, EvtPreLog.PATTERN)
                .description(
                        "Fired when a CoreProtect logger is about to log an action.",
                        "Cancellable."
                )
                .since("0.1.0")
                .requiredPlugins("CoreProtect")
                .examples(
                        "on CoreProtect logging:",
                        "    if username parsed as offlineplayer is op:",
                        "        cancel event"
                );

        final Getter<String, CoreProtectPreLogEvent> getter = new Getter<>() {
            @Override
            public @Nullable String get(CoreProtectPreLogEvent arg) {
                return arg.getUser();
            }
        };

        EventValues.registerEventValue(CoreProtectPreLogEvent.class, String.class, getter, 0);
    }

}
