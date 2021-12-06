package de.nehlen.knockbackffa.manager;

import de.nehlen.knockbackffa.KnockbackFFA;
import de.nehlen.knockbackffa.data.ScoreboardData;
import de.nehlen.knockbackffa.sidebar.Sidebar;
import de.nehlen.knockbackffa.sidebar.SidebarCache;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ScoreboardManager {
    private final KnockbackFFA knockbackFFA;

    private final HashMap<Player, BukkitTask> scoreboardTaskMap = new HashMap<>();

    private final int updateInterval = 1;

    public ScoreboardManager(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    public void setUserScoreboard(final Player player) {
        if (!this.scoreboardTaskMap.containsKey(player))
            this.scoreboardTaskMap.put(player, (new BukkitRunnable() {
                int counter = 0;

                public void run() {
                    ScoreboardManager.this.setScoreboardContent(player, this.counter);
                    this.counter = ++this.counter % (ScoreboardData.values()).length;
                }
            }).runTaskTimer((Plugin) this.knockbackFFA, 0L, 20L));
    }

    public void removeUserScoreboard(Player player) {
        if (this.scoreboardTaskMap.containsKey(player)) {
            ((BukkitTask) this.scoreboardTaskMap.get(player)).cancel();
            this.scoreboardTaskMap.remove(player);
        }
    }

    private void setScoreboardContent(Player player, int pageNumber) {
        ScoreboardData scoreboardData = ScoreboardData.values()[pageNumber];
        this.knockbackFFA.getSidebarCache();
        Sidebar sidebar = SidebarCache.getUniqueCachedSidebar(player);
        sidebar.setDisplayName(scoreboardData.getDisplayName());
        sidebar.setLines(scoreboardData.getLines(), "%kills%",
                Integer.valueOf(this.knockbackFFA.getUserFactory().getKills(player)), "%deaths%",
                Integer.valueOf(this.knockbackFFA.getUserFactory().getDeaths(player)), "%kd%", this.knockbackFFA.getUserFactory().getKDRatio(player));
    }
}
