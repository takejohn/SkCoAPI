package io.github.takejohn.skcoapi;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.Classes;
import io.github.takejohn.skcoapi.elements.conditions.*;
import io.github.takejohn.skcoapi.elements.effects.*;
import io.github.takejohn.skcoapi.elements.events.EvtPreLog;
import io.github.takejohn.skcoapi.elements.expressions.*;
import io.github.takejohn.skcoapi.elements.types.CoreProtectLogs;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SkCoAPI extends JavaPlugin {

    public static final @NotNull CoreProtectAPI coreProtectAPI = getCoreProtectAPI();

    @Override
    public void onEnable() {
        Skript.registerAddon(this);
        Classes.registerClass(CoreProtectLogs.classInfo());
        ExprBlockXCoordinate.register();
        ExprBlockYCoordinate.register();
        ExprBlockZCoordinate.register();
        ExprBlockType.register();
        ExprLoggedBlockData.register();
        ExprPlayerName.register();
        ExprTimestamp.register();
        ExprDate.register();
        ExprActionId.register();
        ExprActionString.register();
        ExprAPIVersion.register();
        Skript.registerCondition(CondRolledBack.class, CondRolledBack.PATTERN);
        ExprLoggedWorldName.register();
        ExprLoggedWorld.register();
        ExprBlockLocation.register();
        Skript.registerCondition(CondEnabled.class, CondEnabled.PATTERN);
        Skript.registerEffect(EffTestAPI.class, EffTestAPI.PATTERN);
        ExprLookup.register();
        ExprRollback.register();
        ExprRestore.register();
        ExprBlockLookup.register();
        ExprSessionLookup.register();
        ExprQueueLookup.register();
        Skript.registerEffect(EffLogChat.class, EffLogChat.PATTERN);
        Skript.registerEffect(EffLogCommand.class, EffLogCommand.PATTERN);
        EffLogPlacement.register();
        EffLogRemoval.register();
        Skript.registerEffect(EffLogContainerTransaction.class, EffLogContainerTransaction.PATTERN);
        Skript.registerEffect(EffLogInteraction.class, EffLogInteraction.PATTERN);
        CondLoggingSucceeded.register();
        Skript.registerCondition(CondPlaced.class, CondPlaced.PATTERN);
        Skript.registerCondition(CondRemoved.class, CondRemoved.PATTERN);
        Skript.registerEffect(EffPurge.class, EffPurge.PATTERN);
        EvtPreLog.register();
        ExprPreLogUserName.register();
    }

    private static @NotNull CoreProtectAPI getCoreProtectAPI() {
        @Nullable Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

        if (!(plugin instanceof CoreProtect)) {
            Bukkit.getLogger().warning("CoreProtect not loaded.");
            Bukkit.getLogger().warning("Cannot continue because CoreProtect does not exist.");
        }

        assert plugin instanceof CoreProtect;
        @NotNull CoreProtectAPI coreProtectAPI = ((CoreProtect)plugin).getAPI();
        if (!coreProtectAPI.isEnabled()) {
            Bukkit.getLogger().warning("CoreProtect API not enabled.");
        }

        final int apiVersion = coreProtectAPI.APIVersion();
        if (apiVersion < 9) {
            Bukkit.getLogger().warning(
                    () -> "CoreProtect API version expected to be 9 or over but is " + apiVersion + "."
            );
        }

        return coreProtectAPI;
    }

}
