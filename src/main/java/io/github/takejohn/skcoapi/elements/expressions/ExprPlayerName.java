package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("User/Player Name of Log")
@Description("The username as a string.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprPlayerName extends LogPropertyExpression<String> {

    private static final String PROPERTY_NAME = "[CoreProtect] (player |user[ ])name[s]";

    public static void register(@NotNull SyntaxRegistry registry) {
        registerProperty(registry, ExprPlayerName.class, String.class, PROPERTY_NAME, ExprPlayerName::new);
    }

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @Nullable String convert(CoreProtectAPI.ParseResult parseResult) {
        return parseResult.getPlayer();
    }

}
