package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

@Name("Log Broke/Removed")
@Description("Logs a block as being removed/broken, and log the block's inventory (if applicable).")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffLogRemoval extends LogBlockUpdateEffect {

    private static final String ACTION_VERB = "(broke|removed)";

    @Override
    protected String actionVerb() {
        return ACTION_VERB;
    }

    @Override
    protected boolean log(String user, Location location, Material type, BlockData blockData) {
        return SkCoAPI.coreProtectAPI.logRemoval(user, location, type, blockData);
    }

    public static void register() {
        registerWithVerb(EffLogRemoval.class, ACTION_VERB);
    }

}
