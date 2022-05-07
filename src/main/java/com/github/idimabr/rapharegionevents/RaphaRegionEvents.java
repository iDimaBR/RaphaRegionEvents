package com.github.idimabr.rapharegionevents;

import com.github.idimabr.rapharegionevents.commands.TemporaryCommand;
import com.github.idimabr.rapharegionevents.listeners.PlayerListener;
import com.github.idimabr.rapharegionevents.managers.ManagerObject;
import com.github.idimabr.rapharegionevents.runnables.ToggleRegionRunnable;
import com.github.idimabr.rapharegionevents.utils.ConfigUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RaphaRegionEvents extends JavaPlugin {

    private static RaphaRegionEvents plugin;
    private static WorldGuardPlugin WGAPI;
    private static ManagerObject MANAGER;
    private static ConfigUtil config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        config = new ConfigUtil(null, "config.yml", false);

        if(!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")){
            getServer().getLogger().info("WorldGuard não foi encontrado!");
            getPluginLoader().disablePlugin(this);
            return;
        }

        WGAPI = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
        MANAGER = new ManagerObject(this);
        MANAGER.loadEvents();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        getCommand("regionevents").setExecutor(new TemporaryCommand());
        new ToggleRegionRunnable(this).runTaskTimerAsynchronously(this, 0, 20L * 5);
        config.saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        config.reloadConfig();
    }

    public static WorldGuardPlugin getWorldGuard() {
        return WGAPI;
    }

    public static RaphaRegionEvents getPlugin() {
        return plugin;
    }

    public static ManagerObject getManager() {
        return MANAGER;
    }

    @Override
    public ConfigUtil getConfig() {
        return config;
    }
}
