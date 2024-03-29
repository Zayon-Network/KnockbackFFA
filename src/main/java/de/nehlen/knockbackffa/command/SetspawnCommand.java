package de.nehlen.knockbackffa.command;

import de.nehlen.knockbackffa.KnockbackFFA;
import de.nehlen.knockbackffa.data.StringData;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCommand implements CommandExecutor {
    private final KnockbackFFA knockbackFFA;

    public SetspawnCommand(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player))
            return true;
        Player player = (Player)commandSender;
        if (!player.hasPermission("permssion.name")) {
            player.sendMessage(StringData.combinate());
            return true;
        }
        if (args.length == 0) {
            Location location = player.getLocation();
            this.knockbackFFA.getLocationConfig().getOrSetDefault("config.location.spawn", location);
            player.sendMessage(StringData.getPrefix() + "§7 Spawn gesetzt§8.");
        }
        return false;
    }
}
