package io.github.takejohn.skcoapi.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Example;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import io.github.takejohn.skcoapi.SkCoAPI;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

@Name("Log Container Transaction")
@Description("Logs any transactions made to a block's inventory immediately after calling the method.")
@Since("0.1.0")
@RequiredPlugins("CoreProtect")
@Example("""
        command /takeall:
            executable by: players
            trigger:
                set {_block} to target block of player
                send "&aTaking items from %{_block}%" to player
                loop all items in the inventory of {_block}:
                    if player cannot hold loop-item:
                        send "&cStopped taking items because your inventory is full!" to player
                        stop
                    give loop-item to player
                    remove loop-item from the inventory of {_block}
                    log that player's name made a transaction to a container at location of {_block}
        """)
public class EffLogContainerTransaction extends LogLocationEffect {

    public static final String PATTERN = "log [that] %string% made [a] transaction [to [a] container] at %location%";

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT,
                SyntaxInfo.simple(EffLogContainerTransaction.class, EffLogContainerTransaction::new, PATTERN));
    }

    @Override
    protected @NotNull String action() {
        return "made a transaction to a container";
    }

    @Override
    protected boolean log(@NotNull String user, @NotNull Location location) {
        return SkCoAPI.getCoreProtectAPI().logContainerTransaction(user, location);
    }

}
