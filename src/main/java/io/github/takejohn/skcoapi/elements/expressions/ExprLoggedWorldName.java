package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprLoggedWorldName extends LogPropertyExpression<String> {

    private static final String PROPERTY_NAME = "logged world name[s]";

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
        return parseResult.worldName();
    }

    public static void register() {
        register(ExprLoggedWorldName.class, String.class, PROPERTY_NAME);
    }

}
