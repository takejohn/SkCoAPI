package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprActionId extends LogPropertyExpression<Integer> {

    private static final String PROPERTY_NAME = "action ID[s]";

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @NotNull Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public @Nullable Integer convert(CoreProtectAPI.ParseResult parseResult) {
        return parseResult.getActionId();
    }

    public static void register() {
        register(ExprActionId.class, Integer.class, PROPERTY_NAME);
    }

}
