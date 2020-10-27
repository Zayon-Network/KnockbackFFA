package de.zayon.knockbackffa.factory;

import de.zayon.knockbackffa.KnockbackFFA;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;
import org.bukkit.entity.Player;

public class UserFactory {
    private final KnockbackFFA knockbackFFA;

    public UserFactory(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    public void createTable() {
        StringBuilder table = new StringBuilder();
        table.append("id INT(11) NOT NULL AUTO_INCREMENT, ");
        table.append("`uuid` VARCHAR(64) NOT NULL UNIQUE, ");
        table.append("`kills` INT(11) NOT NULL, ");
        table.append("`deaths` INT(11) NOT NULL, ");
        table.append("PRIMARY KEY (`id`)");
        this.knockbackFFA.getDatabaseLib().executeUpdateAsync("CREATE TABLE IF NOT EXISTS zayon_knockbackffa_stats (" + table.toString() + ")", resultSet -> {});
    }

    public CompletableFuture<Boolean> userExists(Player player) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        this.knockbackFFA.getDatabaseLib().executeQueryAsync("SELECT id FROM zayon_knockbackffa_stats WHERE uuid = ?", resultSet -> {
            try {
                completableFuture.complete(Boolean.valueOf(resultSet.next()));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }, player.getUniqueId().toString());
        return completableFuture;
    }

    public void createUser(Player player) {
        userExists(player).whenCompleteAsync((exist, throwable) -> {
            if (throwable == null && !exist.booleanValue())
                this.knockbackFFA.getDatabaseLib().executeUpdateAsync("INSERT INTO zayon_knockbackffa_stats (uuid, kills, deaths) VALUES (?, ?, ?)", resultSet -> {}, player.getUniqueId().toString(), Integer.valueOf(0), Integer.valueOf(0));
        });
    }

    public int getKills(Player player) {
        return ((Integer)this.knockbackFFA.getDatabaseLib().get("SELECT kills FROM zayon_knockbackffa_stats WHERE uuid = ?", player.getUniqueId().toString(), "kills")).intValue();
    }

    public int getDeaths(Player player) {
        return ((Integer)this.knockbackFFA.getDatabaseLib().get("SELECT deaths FROM zayon_knockbackffa_stats WHERE uuid = ?", player.getUniqueId().toString(), "deaths")).intValue();
    }

    public void updateKills(Player player, UpdateType updateType, int kills) {
        int newKills = 0;
        if (updateType == UpdateType.ADD) {
            newKills = getKills(player) + kills;
        } else if (updateType == UpdateType.REMOVE) {
            newKills = getKills(player) - kills;
        }
        this.knockbackFFA.getDatabaseLib().executeUpdateAsync("UPDATE zayon_knockbackffa_stats SET kills = ? WHERE uuid = ?", resultSet -> {},Integer.valueOf(newKills), player.getUniqueId().toString() );
    }

    public void updateDeaths(Player player, UpdateType updateType, int deaths) {
        int newDeaths = 0;
        if (updateType == UpdateType.ADD) {
            newDeaths = getDeaths(player) + deaths;
        } else if (updateType == UpdateType.REMOVE) {
            newDeaths = getDeaths(player) - deaths;
        }
        this.knockbackFFA.getDatabaseLib().executeUpdateAsync("UPDATE zayon_knockbackffa_stats SET deaths = ? WHERE uuid = ?", resultSet -> {}, Integer.valueOf(newDeaths), player.getUniqueId().toString());
    }

    public String getKDRatio(Player player) {
        double kd = (double) getKills(player) / (double) getDeaths(player);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(kd);
    }

    public enum UpdateType {
        ADD, REMOVE;
    }
}
