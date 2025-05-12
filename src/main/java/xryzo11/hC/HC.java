package xryzo11.hC;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public final class HC extends JavaPlugin {
    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        getLogger().info("HC enabling...");
        config.addDefault("displayDamageMessages", true);
        config.addDefault("damageMessageMinThreshold", 0);
        config.addDefault("displayHealMessages", true);
        config.addDefault("healMessageMinThreshold", 0.5);
        config.addDefault("sharedHealth", false);
        config.addDefault("sharedHunger", false);
        config.addDefault("sharedEffects", false);
        config.addDefault("sharedInventory", false);
        config.addDefault("sharedExperience", false);
        config.addDefault("autoOp", false);
        config.addDefault("autoScoreboard", true);
        config.addDefault("kickOnDeath", true);
        config.addDefault("stopServerOnDeath", true);
        config.addDefault("autoActionBar", true);
        config.addDefault("giveAllRecipes", true);
        config.addDefault("enableSideLocator", false);
        config.options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListeners(this), this);
        Objects.requireNonNull(getServer().getPluginCommand("hc")).setExecutor(new CommandPrefix(this));
        this.getCommand("hc").setTabCompleter(new CommandPrefix(this));
        getLogger().info("HC enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("HC disabling...");
    }

    public ActionBar getActionBar(UUID uuid) {
        return PlayerListener.actionBars.get(uuid);
    }
}