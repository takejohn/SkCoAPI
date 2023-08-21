package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprTimestamp extends LogPropertyExpression<Number> {

    private static final String PROPERTY_NAME = "timestamp[s]";

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @NotNull Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public @Nullable Number convert(CoreProtectAPI.ParseResult parseResult) {
        return parseResult.getTimestamp();
    }

    public static void register() {
        register(ExprTimestamp.class, Number.class, PROPERTY_NAME);
    }

}
