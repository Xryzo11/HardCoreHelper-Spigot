package xryzo11.hC;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class ReloadConfig implements CommandExecutor {
    private final HC plugin;

    public ReloadConfig(HC plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        PlayerListener.config = plugin.getConfig();
        InventoryListeners.config = plugin.getConfig();
        sender.sendMessage("Config reloaded.");
        sender.sendMessage("Current config values:");
        sender.sendMessage("displayDamageMessages: " + plugin.getConfig().getBoolean("displayDamageMessages"));
        sender.sendMessage("damageMessageMinThreshold: " + plugin.getConfig().getDouble("damageMessageMinThreshold"));
        sender.sendMessage("displayHealMessages: " + plugin.getConfig().getBoolean("displayHealMessages"));
        sender.sendMessage("healMessageMinThreshold: " + plugin.getConfig().getDouble("healMessageMinThreshold"));
        sender.sendMessage("sharedHealth: " + plugin.getConfig().getBoolean("sharedHealth"));
        sender.sendMessage("sharedHunger: " + plugin.getConfig().getBoolean("sharedHunger"));
        sender.sendMessage("sharedEffects: " + plugin.getConfig().getBoolean("sharedEffects"));
        sender.sendMessage("sharedInventory: " + plugin.getConfig().getBoolean("sharedEffects"));
        sender.sendMessage("sharedExperience: " + plugin.getConfig().getBoolean("sharedExperience"));
        sender.sendMessage("autoOp: " + plugin.getConfig().getBoolean("autoOp"));
        sender.sendMessage("autoScoreboard: " + plugin.getConfig().getBoolean("autoScoreboard"));
        sender.sendMessage("kickOnDeath: " + plugin.getConfig().getBoolean("kickOnDeath"));
        sender.sendMessage("stopServerOnDeath: " + plugin.getConfig().getBoolean("stopServerOnDeath"));
        sender.sendMessage("autoActionBar: " + plugin.getConfig().getBoolean("autoActionBar"));
        sender.sendMessage("giveAllRecipes: " + plugin.getConfig().getBoolean("giveAllRecipes"));
        sender.sendMessage("enableSideLocator: " + plugin.getConfig().getBoolean("enableSideLocator"));
        return true;
    }
}
