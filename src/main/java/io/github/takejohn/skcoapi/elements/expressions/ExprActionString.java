package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprActionString extends LogPropertyExpression<String> {

    private static final String PROPERTY_NAME = "action[ (string|text)][s]";

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
        return parseResult.getActionString();
    }

    public static void register() {
        register(ExprActionString.class, String.class, PROPERTY_NAME);
    }

}
