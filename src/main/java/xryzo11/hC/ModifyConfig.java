package xryzo11.hC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModifyConfig implements CommandExecutor {
    HC plugin;

    public ModifyConfig(HC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("save")) {
                plugin.saveConfig();
                plugin.reloadConfig();
                PlayerListener.config = plugin.getConfig();
                sender.sendMessage(ChatColor.GREEN + "Config saved.");
                return false;
            } else if (args[1].equalsIgnoreCase("reset")) {
                File configFile = new File(plugin.getDataFolder(), "config.yml");
                configFile.delete();
                plugin.config.set("displayDamageMessages", true);
                plugin.config.set("damageMessageMinThreshold", 0);
                plugin.config.set("displayHealMessages", true);
                plugin.config.set("healMessageMinThreshold", 0.5);
                plugin.config.set("sharedHealth", false);
                plugin.config.set("sharedHunger", false);
                plugin.config.set("sharedEffects", false);
                plugin.config.set("sharedInventory", false);
                plugin.config.set("sharedExperience", false);
                plugin.config.set("autoOp", false);
                plugin.config.set("autoScoreboard", true);
                plugin.config.set("kickOnDeath", true);
                plugin.config.set("stopServerOnDeath", true);
                plugin.config.set("autoActionBar", true);
                plugin.config.set("giveAllRecipes", true);
                plugin.config.set("enableSideLocator", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                PlayerListener.config = plugin.getConfig();
                sender.sendMessage(ChatColor.GREEN + "Config reset to defaults.");
                return false;
            }
        } else if (args.length == 3 || args.length == 4) {
            String[] keys = {
                    "displayDamageMessages",
                    "damageMessageMinThreshold",
                    "displayHealMessages",
                    "healMessageMinThreshold",
                    "sharedHealth",
                    "sharedHunger",
                    "sharedEffects",
                    "sharedInventory",
                    "sharedExperience",
                    "autoOp",
                    "autoScoreboard",
                    "kickOnDeath",
                    "stopServerOnDeath",
                    "autoActionBar",
                    "giveAllRecipes",
                    "enableSideLocator"
            };
            String key = args[2];
            String value;
            boolean check = false;
            for (String keyCheck : keys) {
                if (keyCheck.equals(key)) {
                    check = true;
                }
            }
            if (!check) {
                sender.sendMessage(ChatColor.DARK_RED + "Invalid key.");
                return true;
            }
            if (args.length == 4) {
                value = args[3];
                if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")) {
                    plugin.getConfig().set(key, Boolean.valueOf(value));
                } else {
                    plugin.getConfig().set(key, Double.valueOf(value));
                }
                sender.sendMessage(ChatColor.YELLOW + "Config updated: " + ChatColor.LIGHT_PURPLE + key + ChatColor.WHITE + " = " + ChatColor.AQUA + value);
                sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.WHITE + "/hc config save" + ChatColor.YELLOW + " to save the config.");
                return false;
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Invalid value.");
                return true;
            }
        }
        return false;
    }
}
