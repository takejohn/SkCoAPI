package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Timestamp")
@Description("The time of the action.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
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
