package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

@Name("Log Placement")
@Description("Logs a block as being placed.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
public class EffLogPlacement extends LogBlockUpdateEffect {

    private static final String ACTION_VERB = "(placed|built)";

    @Override
    protected String actionVerb() {
        return ACTION_VERB;
    }

    @Override
    protected boolean log(String user, Location location, Material type, BlockData blockData) {
        return SkCoAPI.coreProtectAPI.logPlacement(user, location, type, blockData);
    }

    public static void register() {
        registerWithVerb(EffLogPlacement.class, ACTION_VERB);
    }

}
