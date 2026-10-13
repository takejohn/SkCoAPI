package io.github.takejohn.skcoapi;

import ch.njol.skript.Skript;
import io.github.takejohn.skcoapi.elements.CoreProtectModule;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.addon.SkriptAddon;

public final class SkCoAPI extends JavaPlugin {

    /**
     * The oldest CoreProtect API version which provides every method this addon calls.
     */
    private static final int MINIMUM_API_VERSION = 9;

    private static SkCoAPI plugin;

    private static CoreProtectAPI coreProtectAPI;

    @Override
    public void onEnable() {
        plugin = this;

        final @Nullable CoreProtectAPI api = loadCoreProtectAPI();
        if (api == null) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        coreProtectAPI = api;

        if (!Skript.isAcceptRegistrations()) {
            getLogger().severe("Skript no longer accepts syntax registrations. " +
                    "SkCoAPI has to be enabled while the server is starting up (do not load it with /reload or plugin managers).");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        final SkriptAddon addon = Skript.instance().registerAddon(SkCoAPI.class, getName());
        addon.localizer().setSourceDirectories("lang", null);
        addon.loadModules(new CoreProtectModule());
    }

    private @Nullable CoreProtectAPI loadCoreProtectAPI() {
        final @Nullable Plugin coreProtect = getServer().getPluginManager().getPlugin("CoreProtect");
        // Check this before touching any CoreProtect class: the classes of a disabled plugin cannot be loaded,
        // e.g. when CoreProtect has disabled itself because it does not support this Minecraft version.
        if (coreProtect == null || !coreProtect.isEnabled()) {
            getLogger().severe("CoreProtect is not enabled. SkCoAPI cannot work without CoreProtect. " +
                    "Check the log above for why CoreProtect failed to start.");
            return null;
        }
        if (!(coreProtect instanceof CoreProtect)) {
            getLogger().severe("The plugin named CoreProtect is not CoreProtect by PlayPro.");
            return null;
        }

        final CoreProtectAPI api = ((CoreProtect)coreProtect).getAPI();
        if (!api.isEnabled()) {
            getLogger().warning("CoreProtect API is not enabled. Check 'api-enabled' in the config of CoreProtect.");
        }

        final int apiVersion = api.APIVersion();
        if (apiVersion < MINIMUM_API_VERSION) {
            getLogger().warning("CoreProtect API version is expected to be " + MINIMUM_API_VERSION +
                    " or over but is " + apiVersion + ".");
        }
        return api;
    }

    /**
     * Runs a task on the main thread, unless this plugin has already been disabled.
     */
    public static void runTask(@NotNull Runnable runnable) {
        if (plugin.isEnabled()) {
            Bukkit.getScheduler().runTask(plugin, runnable);
        }
    }

    /**
     * Runs a task asynchronously, unless this plugin has already been disabled.
     */
    public static void runTaskAsynchronously(@NotNull Runnable runnable) {
        if (plugin.isEnabled()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
        }
    }

    public static @NotNull SkCoAPI getPlugin() {
        return plugin;
    }

    public static @NotNull CoreProtectAPI getCoreProtectAPI() {
        if (coreProtectAPI == null) {
            throw new IllegalStateException("SkCoAPI is not enabled");
        }
        return coreProtectAPI;
    }

}
