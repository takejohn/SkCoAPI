package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Action ID")
@Description("Get the action ID. (0=removed, 1=placed, 2=interaction)")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprActionId extends LogPropertyExpression<Integer> {

    private static final String PROPERTY_NAME = "[CoreProtect] action ID[s]";

    public static void register(@NotNull SyntaxRegistry registry) {
        registerProperty(registry, ExprActionId.class, Integer.class, PROPERTY_NAME, ExprActionId::new);
    }

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

}
