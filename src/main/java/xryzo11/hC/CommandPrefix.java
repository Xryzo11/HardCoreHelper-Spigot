package xryzo11.hC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static xryzo11.hC.PlayerListener.config;
import static xryzo11.hC.PlayerListener.playerScoreboards;

public class CommandPrefix implements CommandExecutor, TabCompleter {
    private final HC plugin;

    public CommandPrefix(HC plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("hc.command")) {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc <config/locator/help> <args>");
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "config":
                if (!sender.hasPermission("hc.command.config")) {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command.");
                    return false;
                }
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc config <save/reset/reload/modify>");
                    return false;
                }
                switch (args[1].toLowerCase()) {
                    case "save":
                        new ModifyConfig(plugin).onCommand(sender, command, label, args);
                        break;
                    case "reset":
                        new ModifyConfig(plugin).onCommand(sender, command, label, args);
                        break;
                    case "reload":
                        new ReloadConfig(plugin).onCommand(sender, command, label, args);
                        break;
                    case "modify":
                        if (args.length < 3) {
                            sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc config modify <key> <value>");
                            return false;
                        }
                        new ModifyConfig(plugin).onCommand(sender, command, label, args);
                        break;
                    default:
                        sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc config <save/reset/reload/modify>");
                        break;
                }
                break;
            case "locator":
                if (!sender.hasPermission("hc.command.locator")) {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command.");
                    return false;
                }
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc locator <player/off>");
                    return false;
                }
                new ActionBar(plugin).onCommand(sender, command, label, args);
                break;
            case "sidelocator":
                if (!sender.hasPermission("hc.command.sidelocator")) {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command.");
                    return false;
                }
                if (!config.getBoolean("enableSideLocator")) {
                    sender.sendMessage(ChatColor.DARK_RED + "Side locator is disabled in the config.");
                    return false;
                }
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc sidelocator <on/off>");
                    return false;
                }
                if (args.length == 2) {
                    playerScoreboards.get(((Player) sender).getUniqueId()).onCommand(sender, command, label, args);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc sidelocator <on/off>");
                    return false;
                }
            case "help":
                if (!sender.hasPermission("hc.command.help")) {
                    sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command.");
                    return false;
                }
                new HelpCommand().onCommand(sender, command, label, args);
                break;
            default:
                sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc <config/locator/side/help> <args>");
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of("config", "locator", "sidelocator", "help");
        } else if (args.length >= 2 && args[0].equalsIgnoreCase("config")) {
            if (args.length == 2) {
                return List.of("save", "reset", "reload", "modify");
            } else if (args.length >= 3) {
                List<String> completions = new ArrayList<>();
                List<String> arguments = new ArrayList<>();
                arguments.add("displayDamageMessages");
                arguments.add("damageMessageMinThreshold");
                arguments.add("displayHealMessages");
                arguments.add("healMessageMinThreshold");
                arguments.add("sharedHealth");
                arguments.add("sharedHunger");
                arguments.add("sharedEffects");
                arguments.add("sharedInventory");
                arguments.add("sharedExperience");
                arguments.add("autoOp");
                arguments.add("autoScoreboard");
                arguments.add("kickOnDeath");
                arguments.add("stopServerOnDeath");
                arguments.add("autoActionBar");
                arguments.add("giveAllRecipes");
                arguments.add("enableSideLocator");
                List<String> values;
                if (args.length == 3) {
                    StringUtil.copyPartialMatches(args[2], arguments, completions);
                } else if (args.length == 4) {
                    if (args[3] != null && arguments.contains(args[2])) {
                        values = new ArrayList<>();
                        String currentValue = plugin.getConfig().getString(args[2]);
                        if (currentValue != null) {
                            values.add(currentValue);
                        }
                        StringUtil.copyPartialMatches(args[3], values, completions);
                    }
                }
                Collections.sort(completions);
                return completions;
            }
        } else if (args.length >= 2 && args[0].equalsIgnoreCase("locator")) {
            List<String> completions = new ArrayList<>();
            List<String> arguments = new ArrayList<>();
            if (args.length == 2) {
                arguments.add("off");
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    arguments.add(onlinePlayer.getName());
                }
            }
            StringUtil.copyPartialMatches(args[1], arguments, completions);
            Collections.sort(completions);
            return completions;
        } else if (args.length >= 2 && args[0].equalsIgnoreCase("sidelocator")) {
            return List.of("off", "on");
        }
        return List.of();
    }
}
