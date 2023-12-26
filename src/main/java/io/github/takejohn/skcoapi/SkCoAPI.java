package io.github.takejohn.skcoapi;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.Classes;
import io.github.takejohn.skcoapi.elements.conditions.*;
import io.github.takejohn.skcoapi.elements.effects.*;
import io.github.takejohn.skcoapi.elements.events.EvtPreLog;
import io.github.takejohn.skcoapi.elements.expressions.*;
import io.github.takejohn.skcoapi.elements.sections.EffSecLookup;
import io.github.takejohn.skcoapi.elements.sections.EffSecRestore;
import io.github.takejohn.skcoapi.elements.sections.EffSecRollback;
import io.github.takejohn.skcoapi.elements.types.CoreProtectLogs;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SkCoAPI extends JavaPlugin {

    private static CoreProtectAPI coreProtectAPI;

    private static SkCoAPI plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Skript.registerAddon(this);
        Classes.registerClass(CoreProtectLogs.classInfo());
        ExprBlockType.register();
        ExprLoggedBlockData.register();
        ExprPlayerName.register();
        ExprDate.register();
        ExprActionId.register();
        ExprActionString.register();
        ExprAPIVersion.register();
        Skript.registerCondition(CondRolledBack.class, CondRolledBack.PATTERN);
        ExprLoggedWorldName.register();
        ExprBlockLocation.register();
        Skript.registerCondition(CondEnabled.class, CondEnabled.PATTERN);
        Skript.registerEffect(EffTestAPI.class, EffTestAPI.PATTERN);
        EffSecLookup.register();
        EffSecRollback.register();
        EffSecRestore.register();
        ExprResults.register();
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

    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public static void runTaskAsynchronously(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static @NotNull CoreProtectAPI getCoreProtectAPI() {
        if (SkCoAPI.coreProtectAPI != null) {
            return SkCoAPI.coreProtectAPI;
        }

        @Nullable Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

        if (!(plugin instanceof CoreProtect)) {
            Bukkit.getLogger().warning("CoreProtect not loaded.");
            Bukkit.getLogger().warning("Cannot continue because CoreProtect does not exist.");
        }

        assert plugin instanceof CoreProtect;
        @NotNull CoreProtectAPI coreProtectAPI = ((CoreProtect)plugin).getAPI();
        if (!coreProtectAPI.isEnabled()) {
            SkCoAPI.plugin.getLogger().warning("CoreProtect API not enabled.");
        }

        final int apiVersion = coreProtectAPI.APIVersion();
        if (apiVersion < 9) {
            Bukkit.getLogger().warning(
                    () -> "CoreProtect API version expected to be 9 or over but is " + apiVersion + "."
            );
        }

        SkCoAPI.coreProtectAPI = coreProtectAPI;
        return coreProtectAPI;
    }

}
