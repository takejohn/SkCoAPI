package io.github.takejohn.skcoapi.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Logged World Name")
@Description("The name of the world the block is located in.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class ExprLoggedWorldName extends LogPropertyExpression<String> {

    private static final String PROPERTY_NAME = "CoreProtect world name[s]";

    public static void register(@NotNull SyntaxRegistry registry) {
        registerProperty(registry, ExprLoggedWorldName.class, String.class, PROPERTY_NAME, ExprLoggedWorldName::new);
    }

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
        return parseResult.worldName();
    }

}
