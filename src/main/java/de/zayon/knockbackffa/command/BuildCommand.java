package de.zayon.knockbackffa.command;

import de.zayon.knockbackffa.KnockbackFFA;
import de.zayon.knockbackffa.data.StringData;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuildCommand implements CommandExecutor {
    private final KnockbackFFA knockbackFFA;

    @Getter private final ArrayList<Player> editUserMap = new ArrayList<>();

    @Getter private final HashMap<Player, ItemStack[]> editItemMap = (HashMap)new HashMap<>();

    public BuildCommand(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player))
            return true;
        Player player = (Player)commandSender;
        if (!player.hasPermission("lobby.build")) {
            player.sendMessage(StringData.combinate());
            return true;
        }
        if (args.length == 0) {
            if (this.editUserMap.contains(player)) {
                this.editUserMap.remove(player);
                player.setGameMode(GameMode.SURVIVAL);
                ItemStack[] saveItems = this.editItemMap.get(player);
                player.getInventory().setContents(saveItems);
                player.sendMessage(StringData.getPrefix() + "§7Dein Editmodus wurde §cdeaktiviert§7.");
            } else {
                this.editUserMap.add(player);
                player.setGameMode(GameMode.CREATIVE);
                this.editItemMap.put(player, player.getInventory().getContents());
                player.getInventory().clear();
                player.sendMessage(StringData.getPrefix() + "§7Dein Editmodus wurde §aaktiviert§7.");
            }
        } else {
            player.sendMessage(StringData.getPrefix() + "§7Synatx §8- §7/edit");
        }
        return false;
    }
}
