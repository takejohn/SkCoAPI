package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Action String")
@Description("Get the action as a string. (Removal, Placement, Interaction)")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
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
