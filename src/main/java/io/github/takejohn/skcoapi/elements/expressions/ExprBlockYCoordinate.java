package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprBlockYCoordinate extends LogPropertyExpression<Integer> {

    private static final String PROPERTY_NAME = "block (y(-| )(coord[inate]|pos[ition]|loc[ation])|altitude)[s]";

    @Override
    public @NotNull Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @Nullable Integer convert(CoreProtectAPI.ParseResult parseResult) {
        return parseResult.getY();
    }

    public static void register() {
        register(ExprBlockYCoordinate.class, Integer.class, PROPERTY_NAME);
    }

}
