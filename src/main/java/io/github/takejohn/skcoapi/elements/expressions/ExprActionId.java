package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Action ID")
@Description("Get the action ID. (0=removed, 1=placed, 2=interaction)")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
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
