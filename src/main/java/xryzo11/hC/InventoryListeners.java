package xryzo11.hC;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class InventoryListeners implements Listener {
    HC plugin;
    public static FileConfiguration config;

    public InventoryListeners(HC plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    ArrayList<Player> invUpdate = new ArrayList<>();
    public static Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
    boolean isUpdating = false;

    boolean debug = false;

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("InventoryOpenEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("InventoryCloseEvent" + isUpdating);
            if (isUpdating) {
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onInventory(InventoryEvent event) {
        if (event.getInventory().getHolder() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("InventoryEvent" + isUpdating);
            if (isUpdating) {
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("EntityPickupItemEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerDropItemEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("InventoryDragEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
//            if (!(event.getInventory().getType().equals(InventoryType.PLAYER))) return;
            if (debug) Bukkit.broadcastMessage("InventoryClickEvent" + isUpdating);
            if (debug) Bukkit.broadcastMessage("getClick" + event.getClick());
            if (debug) Bukkit.broadcastMessage("getSlot" + event.getSlot());
            if (event.getClick() == ClickType.DROP || event.getClick() == ClickType.CONTROL_DROP) {
                return;
            }
            if (event.getSlot() < 0) {
                return;
            }
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onItemBreak(PlayerItemDamageEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerItemDamageEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerItemConsumeEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerBucketEmptyEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerBucketFillEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("ProjectileLaunchEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onPlayerItemMend(PlayerItemMendEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerItemMendEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerCommandPreprocessEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("HangingPlaceEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerInteractEntityEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event) {
//        if (event.getPlayer() instanceof Player player) {
//            if (debug) Bukkit.broadcastMessage("PlayerInteractEvent" + isUpdating);
//            if (isUpdating) {
//                event.setCancelled(true);
//                return;
//            }
//            updateInventory(player);
//        }
//    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("BlockPlaceEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onEntityShear(PlayerShearEntityEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerShearEntityEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onLeadUse(PlayerLeashEntityEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerLeashEntityEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onDispenseArmor(BlockDispenseArmorEvent event) {
        if (event.getTargetEntity() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("BlockDispenseArmorEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onArmorStand(PlayerArmorStandManipulateEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerArmorStandManipulateEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onPickupArrow(PlayerPickupArrowEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerPickupArrowEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerSwapHandItemsEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerDeathEvent" + isUpdating);
            if (isUpdating) {
                return;
            }
            updateInventory(player);
        }
    }

    @EventHandler
    public void onHeldItemChange(PlayerItemHeldEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (debug) Bukkit.broadcastMessage("PlayerItemHeldEvent" + isUpdating);
            if (isUpdating) {
                event.setCancelled(true);
                return;
            }
            updateInventory(player);
        }
    }

    public void updateInventory(Player player) {
        if (!config.getBoolean("sharedInventory")) return;
        if (debug) Bukkit.broadcastMessage("updateInventory" + isUpdating);
        isUpdating = true;
        inv.setContents(player.getInventory().getContents());
        if (invUpdate.contains(player)) return;
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer != player) {
                invUpdate.add(onlinePlayer);
            }
        }
//        for (Player pInv : invUpdate) {
//            pInv.getInventory().setContents(inv.getContents());
//        }
//        invUpdate.clear();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            updateFinal(player);
        }, 1L);
    }

    public void updateFinal(Player player) {
        inv.setContents(player.getInventory().getContents());
        if (invUpdate.contains(player)) return;
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer != player) {
                onlinePlayer.getInventory().setContents(inv.getContents());
            }
        }
        invUpdate.clear();
        isUpdating = false;
    }
}
