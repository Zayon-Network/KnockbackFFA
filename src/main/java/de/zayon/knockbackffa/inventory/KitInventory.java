package de.zayon.knockbackffa.inventory;

import de.exceptionflug.mccommons.config.shared.ConfigFactory;
import de.exceptionflug.mccommons.config.shared.ConfigWrapper;
import de.exceptionflug.mccommons.config.spigot.SpigotConfig;
import de.exceptionflug.mccommons.inventories.api.CallResult;
import de.exceptionflug.mccommons.inventories.api.Click;
import de.exceptionflug.mccommons.inventories.spigot.design.SpigotOnePageInventoryWrapper;
import de.zayon.knockbackffa.KnockbackFFA;
import de.zayon.knockbackffa.util.Items;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class KitInventory extends SpigotOnePageInventoryWrapper {
    private static final ConfigWrapper CONFIG_WRAPPER = ConfigFactory.create(KnockbackFFA.getKnockbackFFA().getDescription().getName() + "/inventories", KitInventory.class, SpigotConfig.class);

    private static final HashMap<Player, String> kitCache = new HashMap<>();

    public KitInventory(Player player) {
        super(player, CONFIG_WRAPPER);
    }

    public void updateInventory() {
        super.updateInventory();
    }

    public void registerActionHandlers() {
        registerActionHandler("selectKit", click -> {
            String argument = (String) click.getArguments().get(0);
            selectKitHandler((Player) getPlayer(), argument);
            updateKitCache((Player) getPlayer(), argument);
            ((Player) getPlayer()).closeInventory();
            return CallResult.DENY_GRABBING;
        });
    }

    public static void selectKitHandler(Player player, String kitName) {
        if (kitName.equalsIgnoreCase("KnockKnock")) {
            player.getInventory().clear();
            player.getInventory().setItem(0, Items.createItemEnch4(Material.STICK, 0, "§cSchläger", Enchantment.KNOCKBACK));
            player.getInventory().setItem(8, Items.createItem(Material.CHEST, 0, "§cKits §7<Knock Knock§7>"));
        } else if (kitName.equalsIgnoreCase("Enterhaken")) {
            player.getInventory().clear();
            player.getInventory().setItem(0, Items.createItemEnch2(Material.STICK, 0, "§cSchläger", Enchantment.KNOCKBACK));
            player.getInventory().setItem(1, Items.createItem(Material.FISHING_ROD, 0, "§cEnterhaken"));
            player.getInventory().setItem(8, Items.createItem(Material.CHEST, 0, "§cKits §7<Enterhaken§7>"));
        } else if (kitName.equalsIgnoreCase("Überflieger")) {
            player.getInventory().setItem(0, Items.createItemEnch2(Material.STICK, 0, "§cSchläger", Enchantment.KNOCKBACK));
            player.getInventory().setItem(1, Items.createItem(Material.FEATHER, 0, "§cFlugfeder"));
            player.getInventory().setItem(8, Items.createItem(Material.CHEST, 0, "§cKits §7<Überflieger§7>"));
        } else if (kitName.equalsIgnoreCase("Endermann")) {
            player.getInventory().setItem(0, Items.createItemEnch2(Material.STICK, 0, "§cSchläger", Enchantment.KNOCKBACK));
            player.getInventory().setItem(1, Items.createItem(Material.ENDER_PEARL, 0, "§cEnderperle"));
            player.getInventory().setItem(8, Items.createItem(Material.CHEST, 0, "§cKits §7<Endermann§7>"));
        } else if (kitName.equalsIgnoreCase("Bauarbeiter")) {
            player.getInventory().setItem(0, Items.createItemEnch2(Material.STICK, 0, "§cSchläger", Enchantment.KNOCKBACK));
            player.getInventory().setItem(8, Items.createItem(Material.CHEST, 0, "§cKits §7<Bauarbeiter§7>"));
            player.getInventory().setItem(1, Items.createItemA(Material.RED_SANDSTONE, 0, "§cSandstein", 64));
        }
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 3.0F);
    }

    private static void updateKitCache(Player player, String kitName) {
        if (kitCache.containsKey(player))
            kitCache.remove(player);
        kitCache.put(player, kitName);
    }

    public static String getCurrentKit(Player player) {
        return kitCache.get(player);
    }

    public static void setFirstTimeKit(Player player, String defaultKit) {
        if (!kitCache.containsKey(player)) {
            kitCache.put(player, defaultKit);
        } else {
            player.sendMessage("Fehler 003, melde dies dem Team!");
        }
    }
}
