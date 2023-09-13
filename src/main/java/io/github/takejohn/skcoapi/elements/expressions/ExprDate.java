package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import ch.njol.skript.util.Date;

@Name("Date")
@Description("The time of the action.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprDate extends LogPropertyExpression<Date> {

    private static final String PROPERTY_NAME = "date[s]";

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @NotNull Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    public @Nullable Date convert(CoreProtectAPI.ParseResult parseResult) {
        return new Date(parseResult.getTimestamp());
    }

    public static void register() {
        register(ExprDate.class, Date.class, PROPERTY_NAME);
    }

}
