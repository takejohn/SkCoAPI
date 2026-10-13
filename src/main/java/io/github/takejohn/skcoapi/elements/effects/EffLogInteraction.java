package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Log Interaction")
@Description("Logs a block as having been interacted with.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffLogInteraction extends LogLocationEffect {

    public static final String PATTERN = "log [that] %string% interacted [[a] block] at %location%";

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT,
                SyntaxInfo.simple(EffLogInteraction.class, EffLogInteraction::new, PATTERN));
    }

    @Override
    protected @NotNull String action() {
        return "interacted a block";
    }

    @Override
    protected boolean log(@NotNull String user, @NotNull Location location) {
        return SkCoAPI.getCoreProtectAPI().logInteraction(user, location);
    }

}
