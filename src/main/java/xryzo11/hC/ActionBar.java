package xryzo11.hC;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.StringUtil;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static xryzo11.hC.PlayerListener.actionBars;

public class ActionBar implements CommandExecutor {
    private final HC plugin;
    private Player player;
    private Player targetPlayer;
    private Boolean isEnabled = false;
    private BukkitTask repeatingTask = null;

    public ActionBar(HC plugin) {
        this.plugin = plugin;
    }

    public ActionBar(HC plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.targetPlayer = player;
        this.isEnabled = true;
        startRepeatingTask();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        ActionBar actionBar = getActionBar(player);
        if (actionBar == null) {
            actionBar = new ActionBar(plugin, player);
            actionBars.put(((Player) sender).getUniqueId(), actionBar);
            actionBar.targetPlayer = player;
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("off")) {
                player.sendMessage(ChatColor.DARK_GREEN + "Locator disabled.");
                actionBar.isEnabled = false;
                actionBar.stopRepeatingTask();
                return false;
            }
            Player checkTargetPlayer = Bukkit.getPlayer(args[1]);
            if (checkTargetPlayer != null) {
                actionBar.targetPlayer = checkTargetPlayer;
                player.sendMessage(ChatColor.DARK_GREEN + "Locator enabled for " + actionBar.targetPlayer.getName());
                actionBar.isEnabled = true;
                actionBar.startRepeatingTask();
                return false;
            } else {
                player.sendMessage(ChatColor.DARK_RED + "Player not found.");
                return true;
            }
        }
        return false;
    }

    private void sendActionBar() {
        if (this.getEnabled() && this.targetPlayer != null) {
//            Location tgtLoc = new Location(player.getWorld(), 0, 0, 0);
            String message;
            if (this.targetPlayer.isOnline()) {
                this.targetPlayer = Bukkit.getPlayer(this.targetPlayer.getUniqueId());
                if (this.targetPlayer.getWorld() != player.getWorld()) {
                    message = targetPlayer.getName()
                            + ChatColor.RED + " Player is in a different world!";
                } else {
                    message = targetPlayer.getName() + " "
                            + ChatColor.RED + targetPlayer.getLocation().getBlockX() + " "
                            + ChatColor.GREEN + targetPlayer.getLocation().getBlockY() + " "
                            + ChatColor.BLUE + targetPlayer.getLocation().getBlockZ() + " "
                            + ChatColor.DARK_GRAY + getDirection(player, targetPlayer) + " ["
                            + ChatColor.GRAY + (int) player.getLocation().distance(this.targetPlayer.getLocation())
                            + ChatColor.DARK_GRAY + "]";
                }
            } else {
                message = "Player is offline!";
            }
            this.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        }
    }

    private void startRepeatingTask() {
        if (repeatingTask != null && !repeatingTask.isCancelled()) {
            return;
        }
        repeatingTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            sendActionBar();
        }, 0L, 5L);
    }

    private void stopRepeatingTask() {
        if (repeatingTask != null && !repeatingTask.isCancelled()) {
            repeatingTask.cancel();
            repeatingTask = null;
        }
    }

    public static String getDirection(Player player, Player targetPlayer) {
        Location plLoc = player.getLocation();
        Location tgtLoc = targetPlayer.getLocation();
//        Location tgtLoc = new Location(player.getWorld(), 0, 0, 0);

        // Distance check (XZ only)
        Vector diff = tgtLoc.toVector().subtract(plLoc.toVector());
        diff.setY(0);
        if (diff.lengthSquared() <= 25) {
            return "\u2716"; // ✖ Close
        }

        // Calculate angle from player to target
        double dx = tgtLoc.getX() - plLoc.getX();
        double dz = tgtLoc.getZ() - plLoc.getZ();
        double angleToTarget = Math.toDegrees(Math.atan2(-dx, dz)); // Z is forward in Minecraft

        // Normalize angle to 0-360
        angleToTarget = (angleToTarget + 360) % 360;

        // Get player yaw (rotation)
        float yaw = plLoc.getYaw();
        yaw = (yaw + 360) % 360;

        // Difference between where they're looking and where target is
        double relativeAngle = (angleToTarget - yaw + 360) % 360;

        // Return compass arrow based on relative angle
        if (relativeAngle < 22.5 || relativeAngle >= 337.5) {
            return "\u2B06"; // ↑
        } else if (relativeAngle < 67.5) {
            return "\u2B08"; // ↗
        } else if (relativeAngle < 112.5) {
            return "\u2B95"; // →
        } else if (relativeAngle < 157.5) {
            return "\u2B0A"; // ↘
        } else if (relativeAngle < 202.5) {
            return "\u2B07"; // ↓
        } else if (relativeAngle < 247.5) {
            return "\u2B0B"; // ↙
        } else if (relativeAngle < 292.5) {
            return "\u2B05"; // ←
        } else {
            return "\u2B09"; // ↖
        }
    }

    public static ActionBar getActionBar(Player player) {
        return actionBars.get(player.getUniqueId());
    }

    private Player getPlayer() {
        return this.player;
    }
    private Boolean getEnabled() {
        return this.isEnabled;
    }

    public void destroy() {
        if (repeatingTask != null && !repeatingTask.isCancelled()) {
            repeatingTask.cancel();
            repeatingTask = null;
        }
        actionBars.remove(player.getUniqueId());
    }
}
