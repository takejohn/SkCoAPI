package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.*;
import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Player Name")
@Description("The username as a string.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprPlayerName extends LogPropertyExpression<String> {

    private static final String PROPERTY_NAME = "(player |user[ ])name[s]";

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

    public static void register() {
        register(ExprPlayerName.class, String.class, PROPERTY_NAME);
    }

}
