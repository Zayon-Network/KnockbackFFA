package de.nehlen.knockbackffa.listener;

import de.nehlen.knockbackffa.inventory.KitInventory;
import de.nehlen.knockbackffa.KnockbackFFA;
import de.nehlen.knockbackffa.data.StringData;
import de.nehlen.knockbackffa.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerJoinListener implements Listener {
    private final KnockbackFFA knockbackFFA;

    public PlayerJoinListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("§a» §7" + player.getName());
        this.knockbackFFA.getUserFactory().createUser(player);
        Bukkit.getOnlinePlayers().forEach(this.knockbackFFA.getScoreboardManager()::setUserScoreboard);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setItem(8, Items.createItem(Material.CHEST, 0, "§cKits §7<Rechtsklick§7>"));
        player.getInventory().setItem(0, Items.createItemEnch2(Material.STICK, 0, "§cSchläger", Enchantment.KNOCKBACK));
        player.sendTitle("", "§cTeams Verboten!", 20, 60, 0);
        KitInventory.setFirstTimeKit(player, "KnockKnock");
        player.sendMessage(StringData.getPrefix() + "§7Du spielst auf der Map: §e" + this.knockbackFFA.getGeneralConfig().getOrSetDefault("config.map.name", "MapName") + "§7 von §e" + this.knockbackFFA.getGeneralConfig().getOrSetDefault("config.map.builder", "MapBuilder"));
    }

    @EventHandler
    public void handleSpawn(PlayerSpawnLocationEvent event) {
        Player player = event.getPlayer();
        Location location = this.knockbackFFA.getLocationConfig().getLocation("config.location.spawn");
        event.setSpawnLocation(location);
    }
}
