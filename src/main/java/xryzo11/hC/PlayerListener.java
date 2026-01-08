package xryzo11.hC;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.ChatColor;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EnchantingInventory;

import java.util.*;

public class PlayerListener implements Listener {
    private final HC plugin;
    public static FileConfiguration config;
    private ConsoleCommandSender console;
    public static final Map<UUID, ActionBar> actionBars = new HashMap<>();
    public static final Map<UUID, PlayerScoreboard> playerScoreboards = new HashMap<>();
    private double absorption;
    private double health;
    private float saturation;
    private double hunger;
    ArrayList<Player> effectQueue = new ArrayList<>();
    private int experienceLv;
    private float experience;

    public PlayerListener(HC plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.console = plugin.getServer().getConsoleSender();
        if (config.getBoolean("sharedHealth")) {
            this.absorption = 0;
            this.health = 20;
        }
        if (config.getBoolean("sharedHunger")) {
            this.saturation = 5;
            this.hunger = 20;
        }
        if (config.getBoolean("sharedExperience")) {
            this.experience = 0;
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (config.getBoolean("kickOnDeath")) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                player.kickPlayer(event.getDeathMessage());
            }
        }
        if (config.getBoolean("stopServerOnDeath")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            plugin.getServer().shutdown();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (config.getBoolean("autoMatistanSharedInventory")) {
            Bukkit.dispatchCommand(console, "sharedinventory create hc");
            Bukkit.dispatchCommand(console, "sharedinventory add hc " + player.getName());
            Bukkit.dispatchCommand(console, "sharedinventory start hc");
        }
        if (config.getBoolean("autoScoreboard")) {
            org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
            if (manager != null) {
                org.bukkit.scoreboard.Scoreboard scoreboard = manager.getMainScoreboard();
                org.bukkit.scoreboard.Objective objective = scoreboard.getObjective("HP");
                if (objective == null) {
                    objective = scoreboard.registerNewObjective("HP", "health", "HP");
                }
                objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.PLAYER_LIST);

                // Set below name display slot
                org.bukkit.scoreboard.Objective belowNameObjective = scoreboard.getObjective("HP");
                if (belowNameObjective != null) {
                    belowNameObjective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);
                }
            }
        }
        if (config.getBoolean("autoOp")) {
            Bukkit.dispatchCommand(console, "op " + player.getName());
        }
        if (config.getBoolean("autoActionBar")) {
            actionBars.remove(player.getUniqueId());
            ActionBar actionBar = new ActionBar(plugin, player);
            actionBars.put(player.getUniqueId(), actionBar);
        }
        if (config.getBoolean("sharedHealth")) {
            player.setAbsorptionAmount(absorption);
            player.setHealth(health);
        }
        if (config.getBoolean("sharedHunger")) {
            player.setSaturation(saturation);
            player.setFoodLevel((int) hunger);
        }
        if (config.getBoolean("sharedExperience")) {
            player.setLevel(experienceLv);
            player.setExp(experience);
        }
        if (config.getBoolean("sharedInventory")) {
            player.getInventory().setContents(InventoryListeners.inv.getContents());
        }
        if (config.getBoolean("giveAllRecipes")) {
            Bukkit.dispatchCommand(console, "recipe give " + player.getName() + " *");
        }
        if (config.getBoolean("enableSideLocator")) {
            playerScoreboards.remove(player.getUniqueId());
            PlayerScoreboard playerScoreboard = new PlayerScoreboard(player);
            playerScoreboards.put(player.getUniqueId(), playerScoreboard);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ActionBar actionBar = actionBars.get(player.getUniqueId());
        if (actionBar != null) {
            actionBar.destroy();
        }
        actionBars.remove(player.getUniqueId());
        PlayerScoreboard playerScoreboard = playerScoreboards.get(player.getUniqueId());
        if (playerScoreboard != null) {
            playerScoreboard.destroy();
        }
        playerScoreboards.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (config.getBoolean("displayDamageMessages")) {
                if (player.isBlocking()) return;
                double damageAmount = event.getFinalDamage();
                int health = (int) Math.ceil(player.getHealth() + player.getAbsorptionAmount() - damageAmount);
                if (damageAmount >= config.getDouble("damageMessageMinThreshold")) {
                    Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + (double) health / 2 + "\u2764" + ChatColor.GRAY + "] " + ChatColor.AQUA + player.getName() + ChatColor.WHITE + " has taken " + ChatColor.RED + (int) damageAmount + ChatColor.WHITE + " damage!");
                }
            }
            if (config.getBoolean("sharedHealth")) {
                updateStats(player, "health");
            }
        }
    }

    @EventHandler
    public void onPlayerHeal(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (config.getBoolean("displayHealMessages")) {
                double healAmount = Math.round(event.getAmount() * 10.0) / 10.0;
                double health = Math.ceil(player.getHealth() + player.getAbsorptionAmount() - healAmount);
                if (healAmount >= config.getDouble("healMessageMinThreshold")) {
                    Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + health / 2 + "\u2764" + ChatColor.GRAY + "] " + ChatColor.AQUA + player.getName() + ChatColor.WHITE + " has healed " + ChatColor.GREEN + healAmount + ChatColor.WHITE + " health!");
                }
            }
            if (config.getBoolean("sharedHealth")) {
                updateStats(player, "health");
            }
        }
    }

    @EventHandler
    public void onPlayerHungerChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (config.getBoolean("sharedHunger")) {
                updateStats(player, "hunger");
            }
        }
    }

    @EventHandler
    public void onPotionEffectChange(EntityPotionEffectEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (config.getBoolean("sharedEffects")) {
                if (effectQueue.contains(event.getEntity())) return;
                for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                    if (onlinePlayer != player) {
                        effectQueue.add(onlinePlayer);
                    }
                }
                for (int i = 0; i < effectQueue.size(); i++) {
                    if (event.getAction() == EntityPotionEffectEvent.Action.ADDED && event.getNewEffect() != null) {
                        Bukkit.broadcastMessage(player.getName() + " has gained " + event.getNewEffect().getType().getName());
                        effectQueue.get(i).addPotionEffect(Objects.requireNonNull(event.getNewEffect()));
                    } else if (event.getAction() == EntityPotionEffectEvent.Action.REMOVED && event.getOldEffect() != null) {
                        Bukkit.broadcastMessage(player.getName() + " has lost " + event.getOldEffect().getType().getName());
                        effectQueue.get(i).removePotionEffect(event.getOldEffect().getType());
                    }
                }
                effectQueue.clear();
            }
        }
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            if (config.getBoolean("sharedExperience")) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    updateExp(player);
                }, 1L);
            }
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        if (event.getEnchanter() instanceof Player) {
            Player player = (Player) event.getEnchanter();
            if (config.getBoolean("sharedExperience")) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    updateExp(player);
                }, 1L);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (config.getBoolean("sharedExperience")) {
                if (event.getInventory() instanceof AnvilInventory) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        updateExp(player);
                    }, 1L);
                }
            }
        }
    }

    public void updateStats(Player player, String stat) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            updateFinal(player, stat);
        }, 1L);
    }

    public void updateFinal(Player player, String stat) {
        absorption = player.getAbsorptionAmount();
        health = player.getHealth();
        hunger = player.getFoodLevel();
        saturation = player.getSaturation();
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer == player) continue;
            switch (stat) {
                case "health":
                    onlinePlayer.setAbsorptionAmount(absorption);
                    onlinePlayer.setHealth(health);
                    break;
                case "hunger":
                    onlinePlayer.setSaturation(saturation);
                    onlinePlayer.setFoodLevel((int) hunger);
                    break;
            }
        }
    }

    public void updateExp(Player player) {
        experienceLv = player.getLevel();
        experience = player.getExp();
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer == player) continue;
            onlinePlayer.setLevel(experienceLv);
            onlinePlayer.setExp(experience);
        }
    }
}