package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprBlockZCoordinate extends LogPropertyExpression<Integer> {

    private static final String PROPERTY_NAME = "block z(-| )(coord[inate]|pos[ition]|loc[ation])[s]";

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
        return parseResult.getZ();
    }

    public static void register() {
        register(ExprBlockZCoordinate.class, Integer.class, PROPERTY_NAME);
    }

}
