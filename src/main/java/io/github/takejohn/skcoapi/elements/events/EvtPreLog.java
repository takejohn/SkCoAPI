package io.github.takejohn.skcoapi.elements.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import net.coreprotect.event.CoreProtectPreLogEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.bukkit.lang.eventvalue.EventValue;
import org.skriptlang.skript.bukkit.lang.eventvalue.EventValueRegistry;
import org.skriptlang.skript.bukkit.registration.BukkitSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

public class EvtPreLog extends SkriptEvent {

    public static final String PATTERN = "CoreProtect log[ging] [(for %-strings%|[an] action [by %-strings%])]";

    private @Nullable Literal<String> users;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        // The users are given either after "for" or after "action by".
        users = (Literal<String>)(args[0] != null ? args[0] : args[1]);
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

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(
                BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(EvtPreLog.class, "Logging Action")
                        .addEvent(CoreProtectPreLogEvent.class)
                        .addPatterns(PATTERN)
                        .addDescription(
                                "Fired when a CoreProtect logger is about to log an action.",
                                "Cancellable."
                        )
                        .addExample("""
                                on CoreProtect logging:
                                    if username parsed as offlineplayer is op:
                                        cancel event
                                """)
                        .addSince("0.1.0")
                        .addRequiredPlugin("CoreProtect")
                        .supplier(EvtPreLog::new)
                        .build()
        );
    }

    /**
     * Registers 'event-string', the name of the user under which the action will be logged.
     */
    public static void registerEventValues(@NotNull EventValueRegistry registry) {
        registry.register(EventValue.simple(CoreProtectPreLogEvent.class, String.class, CoreProtectPreLogEvent::getUser));
    }

}
