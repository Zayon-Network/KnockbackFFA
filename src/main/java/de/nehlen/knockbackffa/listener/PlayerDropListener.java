package de.nehlen.knockbackffa.listener;

import de.nehlen.knockbackffa.KnockbackFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {
    private final KnockbackFFA knockbackFFA;

    public PlayerDropListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    @EventHandler
    public void handleItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!this.knockbackFFA.getBuildCommand().getEditUserMap().contains(player)) {
            event.setCancelled(true);
        }
    }
}
