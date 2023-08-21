package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprBlockXCoordinate extends LogPropertyExpression<Integer> {

    private static final String PROPERTY_NAME = "block x(-| )(coord[inate]|pos[ition]|loc[ation])[s]";

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
        return parseResult.getX();
    }

    public static void register() {
        register(ExprBlockXCoordinate.class, Integer.class, PROPERTY_NAME);
    }

}
