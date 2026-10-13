package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Example;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.coreprotect.event.CoreProtectPreLogEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("User/Player Name")
@Events("CoreProtect logging")
@Description({
        "Usable only in CoreProtect logging event.",
        "The name of the user under which this action will be logged.",
        "This value can be changed.",
        "This can be referred as 'event-string', which only the getter is provided."
})
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
@Example("""
        on CoreProtect logging:
            if username parsed as offlineplayer is op:
                cancel event
        """)
@Example("""
        on CoreProtect logging:
            set user name to "%user name%'s hand"
        """)
public class ExprPreLogUserName extends SimpleExpression<String> {

    public static final String PATTERN = "[the] (user[ ]|player )name";

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION,
                SyntaxInfo.Expression.builder(ExprPreLogUserName.class, String.class)
                        .priority(SyntaxInfo.SIMPLE)
                        .supplier(ExprPreLogUserName::new)
                        .addPattern(PATTERN)
                        .build());
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed,
                        SkriptParser.@NotNull ParseResult parseResult) {
        return getParser().isCurrentEvent(CoreProtectPreLogEvent.class);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "user name";
    }

    @Override
    protected String @NotNull[] get(@NotNull Event e) {
        if (!(e instanceof CoreProtectPreLogEvent preLogEvent)) {
            return new String[0];
        }
        return new String[]{preLogEvent.getUser()};
    }

    @Override
    public Class<?> @Nullable[] acceptChange(Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    public void change(@NotNull Event e, Object @Nullable[] delta, Changer.@NotNull ChangeMode mode) {
        if (!(e instanceof CoreProtectPreLogEvent preLogEvent)) {
            return;
        }
        final String newUser = delta != null ? (String)delta[0] : null;
        // CoreProtect rejects empty user names.
        if (newUser != null && !newUser.isEmpty()) {
            preLogEvent.setUser(newUser);
        }
    }

}
