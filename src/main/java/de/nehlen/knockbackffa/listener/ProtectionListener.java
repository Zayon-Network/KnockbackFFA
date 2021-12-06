package de.nehlen.knockbackffa.listener;

import de.nehlen.knockbackffa.inventory.KitInventory;
import de.nehlen.knockbackffa.KnockbackFFA;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class ProtectionListener implements Listener {
    private final KnockbackFFA knockbackFFA;

    public ProtectionListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    @EventHandler
    public void handlePlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (KitInventory.getCurrentKit(player).equalsIgnoreCase("Bauarbeiter")) {
            if (player.getLocation().getY() >= 60.0D) {
                event.setCancelled(true);
            } else if (block.getType().equals(Material.RED_SANDSTONE)) {
                Bukkit.getScheduler().runTaskLater((Plugin) this.knockbackFFA, () -> {
                    block.setType(Material.REDSTONE_BLOCK);
                    Bukkit.getScheduler().runTaskLater((Plugin) this.knockbackFFA, () -> {
                        block.setType(Material.AIR);
                    }, 40L);
                }, 40L);
            } else {
                event.setCancelled(true);
            }
        } else if (!this.knockbackFFA.getBuildCommand().getEditUserMap().contains(player)) {
            if (KitInventory.getCurrentKit(player).equalsIgnoreCase("Bauarbeiter")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handlePlace(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!this.knockbackFFA.getBuildCommand().getEditUserMap().contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleEntityDamage(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        if (player.getLocation().getY() >= 60.0D)
            event.setCancelled(true);
    }
}
