package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Block Coordinate")
@Description({
        "The X/Y/Z coordinate of the block.",
        "`block altitude` is an alias of `block y-coordinate`."
})
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
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
