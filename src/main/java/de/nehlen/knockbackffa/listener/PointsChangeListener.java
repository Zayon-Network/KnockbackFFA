package de.nehlen.knockbackffa.listener;

import de.nehlen.gameapi.PointsAPI.PointsAPI;
import de.nehlen.gameapi.PointsAPI.PointsChangeEvent;
import de.nehlen.knockbackffa.KnockbackFFA;
import de.nehlen.knockbackffa.helper.ActionbarHelper;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PointsChangeListener implements Listener {

    private final KnockbackFFA knockbackFFA;

    public PointsChangeListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    @EventHandler
    public void handlePointChange(PointsChangeEvent event) {

        if(event.getType().equals(PointsAPI.UpdateType.ADD)) {
            Player player = event.getPlayer().getPlayer();

            ActionbarHelper.sendActionBar(player, "§a+ " + event.getPoints() + " Punkte");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0);
        }
    }
}
