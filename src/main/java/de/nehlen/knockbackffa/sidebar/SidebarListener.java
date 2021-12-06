package de.nehlen.knockbackffa.sidebar;

import de.nehlen.knockbackffa.KnockbackFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class SidebarListener implements Listener {
    private final KnockbackFFA knockbackFFA;

    public SidebarListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SidebarCache.removeCachedSidebar(player);
        this.knockbackFFA.getServer().getPluginManager().registerEvents(this, (Plugin)this.knockbackFFA);
    }
}
