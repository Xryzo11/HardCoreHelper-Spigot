package xryzo11.hC;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class PlayerScoreboard implements CommandExecutor {
    private final HC plugin;
    private final FileConfiguration config;

    private Player player;
    private Scoreboard scoreboard;
    private Objective objective;
    private Score p1;
    private Score p2;
    private Score p3;
    private Score p4;
    private Score p5;
    private Score coords;
    private boolean isEnabled;

    private final PlayerDistance dummy;

    private BukkitTask repeatingTask;

    public PlayerScoreboard(Player player) {
        this.plugin = HC.getPlugin(HC.class);
        this.config = plugin.getConfig();
        this.player = player;
        this.isEnabled = false;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("-==" + player.getDisplayName() + "==-", "dummy");
        objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);
        dummy = new PlayerDistance(player, 10);
        objective.getScore("Coordinates:").setScore(8);
        coords = objective.getScore("coords");
        coords.setScore(7);
        objective.getScore(" ").setScore(6);
        p1 = objective.getScore("1");
        p1.setScore(5);
        p2 = objective.getScore("2");
        p2.setScore(4);
        p3 = objective.getScore("3");
        p3.setScore(3);
        p4 = objective.getScore("4");
        p4.setScore(2);
        p5 = objective.getScore("5");
        p5.setScore(1);
        scoreboard.resetScores(coords.getEntry());
        scoreboard.resetScores(p1.getEntry());
        scoreboard.resetScores(p2.getEntry());
        scoreboard.resetScores(p3.getEntry());
        scoreboard.resetScores(p4.getEntry());
        scoreboard.resetScores(p5.getEntry());
        updateScore();
        repeatingTask = Bukkit.getScheduler().runTaskTimer(plugin, this::updateScore, 0L, 5L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!config.getBoolean("enableSideLocator")) {
            sender.sendMessage(ChatColor.DARK_RED + "Side locator is disabled in the config.");
            return false;
        }
        if (sender instanceof Player) {
            if (args.length == 2) {
                Player player = (Player) sender;
                if (args[1].equalsIgnoreCase("on")) {
                    player.setScoreboard(scoreboard);
                    player.sendMessage(ChatColor.DARK_GREEN + "Side locator enabled.");
                    this.isEnabled = true;
                    return true;
                } else if (args[1].equalsIgnoreCase("off")) {
                    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    player.sendMessage(ChatColor.DARK_GREEN + "Side locator disabled.");
                    this.isEnabled = false;
                    return true;
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc sidelocator <on/off>");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Usage: /hc sidelocator <on/off>");
                return false;
            }
        }
        return false;
    }

    private String lastP1 = "---", lastP2 = "---", lastP3 = "---", lastP4 = "---", lastP5 = "---";

//    public void updateScore() {
//        if (!isEnabled) return;
//        if (!config.getBoolean("enableSideLocator")) {
//            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
//            this.isEnabled = false;
//            return;
//        }
//        ArrayList<PlayerDistance> onlinePlayersDistance = new ArrayList<>();
//        for (Player p : Bukkit.getOnlinePlayers()) {
//            if (p != this.player) {
//                int distance = (int) player.getLocation().distance(p.getLocation());
//                onlinePlayersDistance.add(new PlayerDistance(p, distance));
//            }
//        }
//
//        PlayerDistance pl1 = dummy;
//        PlayerDistance pl2 = dummy;
//        PlayerDistance pl3 = dummy;
//        PlayerDistance pl4 = dummy;
//        PlayerDistance pl5 = dummy;
//
//        for (PlayerDistance playerDistance : onlinePlayersDistance) {
//            if (pl1 == dummy || playerDistance.getDistance() < pl1.getDistance()) {
//                pl5 = pl4;
//                pl4 = pl3;
//                pl3 = pl2;
//                pl2 = pl1;
//                pl1 = playerDistance;
//            } else if (pl2 == dummy || playerDistance.getDistance() < pl2.getDistance()) {
//                pl5 = pl4;
//                pl4 = pl3;
//                pl3 = pl2;
//                pl2 = playerDistance;
//            } else if (pl3 == dummy || playerDistance.getDistance() < pl3.getDistance()) {
//                pl5 = pl4;
//                pl4 = pl3;
//                pl3 = playerDistance;
//            } else if (pl4 == dummy || playerDistance.getDistance() < pl4.getDistance()) {
//                pl5 = pl4;
//                pl4 = playerDistance;
//            } else if (pl5 == dummy || playerDistance.getDistance() < pl5.getDistance()) {
//                pl5 = playerDistance;
//            }
//        }
//
//        scoreboard.resetScores(coords.getEntry());
//        coords = objective.getScore(this.player.getLocation().getBlockX() + " " + this.player.getLocation().getBlockY() + " " + this.player.getLocation().getBlockZ());
//
//        scoreboard.resetScores(lastP1);
//        scoreboard.resetScores(lastP2);
//        scoreboard.resetScores(lastP3);
//        scoreboard.resetScores(lastP4);
//        scoreboard.resetScores(lastP5);
//
//        lastP1 = pl1 != dummy ? "[" + pl1.getDistance() + "] " + ActionBar.getDirection(this.player, pl1.getPlayer()) + " " + pl1.getPlayer().getDisplayName() : "---";
//        lastP2 = pl2 != dummy ? "[" + pl2.getDistance() + "] " + ActionBar.getDirection(this.player, pl2.getPlayer()) + " " + pl2.getPlayer().getDisplayName() : "---";
//        lastP3 = pl3 != dummy ? "[" + pl3.getDistance() + "] " + ActionBar.getDirection(this.player, pl3.getPlayer()) + " " + pl3.getPlayer().getDisplayName() : "---";
//        lastP4 = pl4 != dummy ? "[" + pl4.getDistance() + "] " + ActionBar.getDirection(this.player, pl4.getPlayer()) + " " + pl4.getPlayer().getDisplayName() : "---";
//        lastP5 = pl5 != dummy ? "[" + pl5.getDistance() + "] " + ActionBar.getDirection(this.player, pl5.getPlayer()) + " " + pl5.getPlayer().getDisplayName() : "---";
//
//        p1 = objective.getScore(lastP1);
//        p2 = objective.getScore(lastP2);
//        p3 = objective.getScore(lastP3);
//        p4 = objective.getScore(lastP4);
//        p5 = objective.getScore(lastP5);
//
//        coords.setScore(7);
//        p1.setScore(5);
//        p2.setScore(4);
//        p3.setScore(3);
//        p4.setScore(2);
//        p5.setScore(1);
//
//        player.setScoreboard(scoreboard);
//    }

    public void updateScore() {
        if (!isEnabled) return;
        if (!config.getBoolean("enableSideLocator")) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            this.isEnabled = false;
            return;
        }

        // Use a fixed-size array instead of ArrayList for better performance
        PlayerDistance[] onlinePlayersDistance = new PlayerDistance[Bukkit.getOnlinePlayers().size() - 1];
        int index = 0;

        // Collect players and calculate distances
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(this.player)) {
                if (p.getWorld() != this.player.getWorld()) {
                    continue;
                }
                onlinePlayersDistance[index++] = new PlayerDistance(p, (int) player.getLocation().distance(p.getLocation()));
            }
        }

        // Sort the array using a simple selection sort for the top 5 players
        Arrays.sort(onlinePlayersDistance, 0, index, Comparator.comparingInt(PlayerDistance::getDistance));

        // Prepare entries for the 5 nearest players
        String[] newPs = new String[5];
        String[] lastPs = {lastP1, lastP2, lastP3, lastP4, lastP5}; // Initialize lastPs here
        for (int i = 0; i < 5; i++) {
            if (i < index) {
                PlayerDistance pd = onlinePlayersDistance[i];
                newPs[i] = "[" + pd.getDistance() + "] " + ActionBar.getDirection(this.player, pd.getPlayer()) + " " + pd.getPlayer().getDisplayName();
            } else {
                newPs[i] = "---";
            }
            scoreboard.resetScores(lastPs[i]);
        }

        // Update lastP* fields
        lastP1 = newPs[0];
        lastP2 = newPs[1];
        lastP3 = newPs[2];
        lastP4 = newPs[3];
        lastP5 = newPs[4];

        // Update coordinates
        String coordsEntry = this.player.getLocation().getBlockX() + " " + this.player.getLocation().getBlockY() + " " + this.player.getLocation().getBlockZ();
        scoreboard.resetScores(coords.getEntry());
        coords = objective.getScore(coordsEntry);
        coords.setScore(7);

        // Set new scores
        for (int i = 0; i < 5; i++) {
            if (i < newPs.length) {
                objective.getScore(newPs[i]).setScore(5 - i);
            }
        }

        player.setScoreboard(scoreboard);
    }


    public class PlayerDistance {
        private final Player player;
        private final int distance;

        public PlayerDistance(Player player, int distance) {
            this.player = player;
            this.distance = distance;
        }

        public Player getPlayer() {
            return player;
        }

        public int getDistance() {
            return distance;
        }
    }

    public void destroy() {
        if (repeatingTask != null && !repeatingTask.isCancelled()) {
            repeatingTask.cancel();
            repeatingTask = null;
        }
        this.player = null;
        this.scoreboard = null;
        this.objective = null;
    }
}
