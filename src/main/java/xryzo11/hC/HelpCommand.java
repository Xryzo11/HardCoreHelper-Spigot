package xryzo11.hC;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== HC Help ===");
        sender.sendMessage(ChatColor.YELLOW + "/hc help" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Show this help message");
        sender.sendMessage(ChatColor.YELLOW + "/hc config" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Manage the configuration file");
        sender.sendMessage(ChatColor.YELLOW + "/hc locator" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Enable or disable the locator");
        sender.sendMessage(ChatColor.YELLOW + "/hc sidelocator" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Locator with 5 nearest players");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== HC Config Help ===");
        sender.sendMessage(ChatColor.YELLOW + "/hc config save" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Save the configuration file");
        sender.sendMessage(ChatColor.YELLOW + "/hc config reset" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Reset the configuration file to defaults");
        sender.sendMessage(ChatColor.YELLOW + "/hc config reload" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Reload the configuration file");
        sender.sendMessage(ChatColor.YELLOW + "/hc config modify" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Modify an entry in the configuration file");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== HC Config Modify Help ===");
        sender.sendMessage(ChatColor.YELLOW + "/hc config modify <key> <value>" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Modifying an entry");
        sender.sendMessage(ChatColor.YELLOW + "/hc config modify autoOp true" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Example boolean usage");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== HC Locator Help ===");
        sender.sendMessage(ChatColor.YELLOW + "/hc locator <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Enable/set the locator to a player");
        sender.sendMessage(ChatColor.YELLOW + "/hc locator off" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Disable the locator");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== HC Side Locator Help ===");
        sender.sendMessage(ChatColor.YELLOW + "/hc sidelocator on" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Enable the side locator");
        sender.sendMessage(ChatColor.YELLOW + "/hc sidelocator off" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Disable the side locator");
        return false;
    }
}
