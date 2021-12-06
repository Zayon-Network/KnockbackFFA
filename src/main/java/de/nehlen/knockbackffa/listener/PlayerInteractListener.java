package de.nehlen.knockbackffa.listener;

import de.exceptionflug.mccommons.inventories.spigot.utils.Schedulable;
import de.nehlen.knockbackffa.inventory.KitInventory;
import de.nehlen.knockbackffa.KnockbackFFA;
import de.nehlen.knockbackffa.data.StringData;
import de.nehlen.knockbackffa.util.Items;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerInteractListener implements Listener, Schedulable {
    private final KnockbackFFA knockbackFFA;

    public PlayerInteractListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    public static HashMap<Player, BukkitRunnable> chargeItem = new HashMap<>();

    @EventHandler
    public void handleInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            if (player.getInventory().getItemInMainHand().getType().equals(Material.CHEST)) {
                if (player.getLocation().getY() <= 60.0D) {
                    player.sendMessage(StringData.getPrefix() + "Hier kannst du kein Kit mehr auswählen");
                    return;
                }
                player.openInventory((Inventory)(new KitInventory(player)).build());
            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.FEATHER)) {
                Vector vector = player.getLocation().getDirection().multiply(3.0D).setY(2.0D);
                player.setVelocity(vector);
                player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 3.0F);
                player.getInventory().setItem(1, Items.createItem(Material.FIRE_CHARGE, 0, "wird aufgeladen..."));
                if (!chargeItem.containsKey(player))
                    chargeItem.put(player, new BukkitRunnable() {
                        int counter = 11;

                        public void run() {
                            if (this.counter == 0) {
                                ((BukkitRunnable)PlayerInteractListener.chargeItem.get(player)).cancel();
                                PlayerInteractListener.chargeItem.remove(player);
                                player.getInventory().setItem(1, Items.createItem(Material.FEATHER, 0, "§cFlugfeder"));
                                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
                            }
                            this.counter--;
                        }
                    });
                ((BukkitRunnable)chargeItem.get(player)).runTaskTimerAsynchronously((Plugin)this.knockbackFFA, 0L, 20L);
            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.ENDER_PEARL)) {
                laterAsync(() -> player.getInventory().setItem(1, Items.createItem(Material.FIRE_CHARGE, 0, "wird aufgeladen...")), 5L);
                if (!chargeItem.containsKey(player))
                    chargeItem.put(player, new BukkitRunnable() {
                        int counter = 11;

                        public void run() {
                            if (this.counter == 0) {
                                ((BukkitRunnable)PlayerInteractListener.chargeItem.get(player)).cancel();
                                PlayerInteractListener.chargeItem.remove(player);
                                player.getInventory().setItem(1, Items.createItem(Material.ENDER_PEARL, 0, "§cEnderperle"));
                                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
                            }
                            this.counter--;
                        }
                    });
                ((BukkitRunnable)chargeItem.get(player)).runTaskTimerAsynchronously((Plugin)this.knockbackFFA, 0L, 20L);
            }
    }
}
