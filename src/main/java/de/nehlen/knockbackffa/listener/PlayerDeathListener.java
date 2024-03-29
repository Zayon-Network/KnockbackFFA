package de.nehlen.knockbackffa.listener;

import de.nehlen.gameapi.Gameapi;
import de.nehlen.gameapi.PointsAPI.PointsAPI;
import de.nehlen.knockbackffa.KnockbackFFA;
import de.nehlen.knockbackffa.data.StringData;
import de.nehlen.knockbackffa.factory.UserFactory;
import de.nehlen.knockbackffa.inventory.KitInventory;
import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PlayerDeathListener implements Listener {
    private final KnockbackFFA knockbackFFA;

    public PlayerDeathListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    @EventHandler
    public void handleDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        Random random = new Random();

        Bukkit.getScheduler().runTaskAsynchronously(KnockbackFFA.getKnockbackFFA(), new Runnable() {
            @Override
            public void run() {
                PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
                ((CraftPlayer) player).getHandle().playerConnection.a(packet);
            }
        });

        event.setDeathMessage(null);
        event.getDrops().clear();
        if (player.getKiller() != null) {
            Bukkit.broadcastMessage(StringData.getPrefix() + this.knockbackFFA.getGroupManager().getGroupColor(player) + player.getName() + " §7wurde von " + this.knockbackFFA.getGroupManager().getGroupColor(killer) + killer.getName() + " §7getötet.");
        } else {
            Bukkit.broadcastMessage(StringData.getPrefix() + this.knockbackFFA.getGroupManager().getGroupColor(player) + player.getName() + " §7ist gestorben.");
        }
        if (player.getKiller() != null) {
            this.knockbackFFA.getUserFactory().updateKills(killer, UserFactory.UpdateType.ADD, 1);
            int points = random.nextInt((10 - 5) + 1) + 5;
            Gameapi.getGameapi().getPointsAPI().updatePoints(killer, PointsAPI.UpdateType.ADD, (points));
        }
        this.knockbackFFA.getUserFactory().updateDeaths(player, UserFactory.UpdateType.ADD, 1);
        if (PlayerInteractListener.chargeItem.containsKey(player)) {
            ((BukkitRunnable) PlayerInteractListener.chargeItem.get(player)).cancel();
            PlayerInteractListener.chargeItem.remove(player);
        }
    }

    @EventHandler
    public void handleRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location location = this.knockbackFFA.getLocationConfig().getLocation("config.location.spawn");
        event.setRespawnLocation(location);
        KitInventory.selectKitHandler(player, KitInventory.getCurrentKit(player));
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getY() <= 25.0D)
            player.setHealth(0.0D);
    }

    @EventHandler
    public void handleEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            event.setCancelled(true);
    }
}
