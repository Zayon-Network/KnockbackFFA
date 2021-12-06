package de.nehlen.knockbackffa.listener;

import de.nehlen.knockbackffa.KnockbackFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final KnockbackFFA knockbackFFA;

    public PlayerQuitListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("§c» §7" + player.getName());
        this.knockbackFFA.getScoreboardManager().removeUserScoreboard(player);
    }
}
