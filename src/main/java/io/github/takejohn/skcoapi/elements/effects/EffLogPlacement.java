package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Example;
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

@Name("Log Placement")
@Description("Logs a block as being placed.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
@Example("""
        command /extinguishedcampfire:
            executable by: players
            trigger:
                set {_block} to campfire[lit=false]
                set block at player to {_block}
                log that player's name placed {_block} at location of player
        """)
public class EffLogPlacement extends LogBlockUpdateEffect {

    private static final String ACTION_VERB = "(placed|built)";

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT,
                SyntaxInfo.simple(EffLogPlacement.class, EffLogPlacement::new, patternWithVerb(ACTION_VERB)));
    }

    @Override
    protected @NotNull String actionVerb() {
        return "placed";
    }

    @Override
    protected boolean log(@NotNull String user, @NotNull Location location, @NotNull Material type,
                          @Nullable BlockData blockData) {
        return SkCoAPI.getCoreProtectAPI().logPlacement(user, location, type, blockData);
    }

}
