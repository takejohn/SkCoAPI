package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprLoggedWorld extends LogPropertyExpression<World> {

    private static final String PROPERTY_NAME = "logged world[s]";

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @NotNull Class<? extends World> getReturnType() {
        return World.class;
    }

    @Override
    public @Nullable World convert(CoreProtectAPI.ParseResult parseResult) {
        return Bukkit.getWorld(parseResult.worldName());
    }

    public static void register() {
        register(ExprLoggedWorld.class, World.class, PROPERTY_NAME);
    }

}
