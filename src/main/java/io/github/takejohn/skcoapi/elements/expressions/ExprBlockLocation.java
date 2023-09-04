package io.github.takejohn.skcoapi.elements.expressions;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ExprBlockLocation extends LogPropertyExpression<Location> {

    private static final String PROPERTY_NAME = "block (location|position)[s]";

    @Override
    public @NotNull Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    protected @NotNull String getPropertyName() {
        return PROPERTY_NAME;
    }

    @Override
    public @Nullable Location convert(CoreProtectAPI.ParseResult parseResult) {
        return new Location(Bukkit.getWorld(parseResult.worldName()),
                parseResult.getX() + 0.5, parseResult.getY() + 0.5, parseResult.getZ() + 0.5);
    }

    public static void register() {
        register(ExprBlockLocation.class, Location.class, PROPERTY_NAME);
    }

}
