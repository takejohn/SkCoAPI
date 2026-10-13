package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Log Broke/Removed")
@Description("Logs a block as being removed/broken, and log the block's inventory (if applicable).")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffLogRemoval extends LogBlockUpdateEffect {

    private static final String ACTION_VERB = "(broke|removed)";

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT,
                SyntaxInfo.simple(EffLogRemoval.class, EffLogRemoval::new, patternWithVerb(ACTION_VERB)));
    }

    @Override
    protected @NotNull String actionVerb() {
        return "removed";
    }

    @Override
    protected boolean log(@NotNull String user, @NotNull Location location, @NotNull Material type,
                          @Nullable BlockData blockData) {
        return SkCoAPI.getCoreProtectAPI().logRemoval(user, location, type, blockData);
    }

}
