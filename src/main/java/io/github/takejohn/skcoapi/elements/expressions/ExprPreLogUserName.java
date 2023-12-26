package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.coreprotect.event.CoreProtectPreLogEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("User/Player Name")
@Events("CoreProtect logging")
@Description({
        "Usable only in CoreProtect logging event.",
        "The name of the user under which this action will be logged.",
        "This value can be changed.",
        "This can be referred as `event-string`, which only the getter is provided."
})
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprPreLogUserName extends SimpleExpression<String> {

    public static final String PATTERN = "[the] (user[ ]|player )name";

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
        return new String[]{((CoreProtectPreLogEvent)e).getUser()};
    }

    @Override
    public @NotNull Class<?> @Nullable[] acceptChange(Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    public void change(@NotNull Event e, Object @Nullable[] delta, Changer.@NotNull ChangeMode mode) {
        final String newUser = delta != null ? (String)delta[0] : null;
        if (newUser != null) {
            ((CoreProtectPreLogEvent)e).setUser(newUser);
        }
    }

    public static void register() {
        Skript.registerExpression(ExprPreLogUserName.class, String.class, ExpressionType.SIMPLE, PATTERN);
    }

}
