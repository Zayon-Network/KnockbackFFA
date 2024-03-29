package de.nehlen.knockbackffa;

import de.exceptionflug.mccommons.config.shared.ConfigFactory;
import de.exceptionflug.mccommons.config.spigot.SpigotConfig;
import de.nehlen.gameapi.Gameapi;
import de.nehlen.knockbackffa.factory.UserFactory;
import de.nehlen.knockbackffa.listener.*;
import de.nehlen.knockbackffa.manager.GroupManager;
import de.nehlen.knockbackffa.manager.ScoreboardManager;
import de.nehlen.knockbackffa.sidebar.SidebarCache;
import de.nehlen.knockbackffa.command.BuildCommand;
import de.nehlen.knockbackffa.command.SetspawnCommand;
import java.io.File;

import de.nehlen.knockbackffa.util.DatabaseLib;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

public class KnockbackFFA extends JavaPlugin {

    @Getter private static KnockbackFFA knockbackFFA;
    @Getter private SpigotConfig generalConfig;
    @Getter private SpigotConfig locationConfig;
    @Getter private ProtectionListener protectionListener;
    @Getter private PlayerJoinListener playerJoinListener;
    @Getter private PlayerInteractListener playerInteractListener;
    @Getter private PlayerDeathListener playerDeathListener;
    @Getter private PlayerFishListener playerFishListener;
    @Getter private PlayerQuitListener playerQuitListener;
    @Getter private BuildCommand buildCommand;
    @Getter private SetspawnCommand setspawnCommand;
    @Getter private SidebarCache sidebarCache;
    @Getter private ScoreboardManager scoreboardManager;
    @Getter private GroupManager groupManager;
    @Getter private DatabaseLib databaseLib;
    @Getter private UserFactory userFactory;
    @Getter private PlayerDropListener playerDropListener;
    @Getter private ServerPingListener serverPingListener;
    @Getter private PointsChangeListener pointsChangeListener;


    public void onEnable() {
        knockbackFFA = this;
        this.generalConfig = (SpigotConfig)ConfigFactory.create(new File(getDataFolder(), "general_settings.yml"), SpigotConfig.class);
        this.locationConfig = (SpigotConfig)ConfigFactory.create(new File(getDataFolder(), "location_settings.yml"), SpigotConfig.class);
        this.sidebarCache = new SidebarCache();
        this.scoreboardManager = new ScoreboardManager(this);
        this.groupManager = new GroupManager();
        this.databaseLib = new DatabaseLib(this);
        this.userFactory = new UserFactory(this);
        this.playerInteractListener = new PlayerInteractListener(this);
        this.protectionListener = new ProtectionListener(this);
        this.playerJoinListener = new PlayerJoinListener(this);
        this.playerQuitListener = new PlayerQuitListener(this);
        this.playerDeathListener = new PlayerDeathListener(this);
        this.playerFishListener = new PlayerFishListener();
        this.playerDropListener = new PlayerDropListener(this);
        this.serverPingListener = new ServerPingListener(this);
        this.pointsChangeListener = new PointsChangeListener(this);
        this.buildCommand = new BuildCommand(this);
        this.setspawnCommand = new SetspawnCommand(this);
        this.userFactory.createTable();
        getCommand("build").setExecutor(this.buildCommand);
        getCommand("setspawn").setExecutor(this.setspawnCommand);
        Bukkit.getPluginManager().registerEvents(this.protectionListener, this);
        Bukkit.getPluginManager().registerEvents(this.playerJoinListener, this);
        Bukkit.getPluginManager().registerEvents(this.playerQuitListener, this);
        Bukkit.getPluginManager().registerEvents(this.playerInteractListener, this);
        Bukkit.getPluginManager().registerEvents(this.playerDeathListener, this);
        Bukkit.getPluginManager().registerEvents(this.playerFishListener, this);
        Bukkit.getPluginManager().registerEvents(this.playerDropListener, this);
        Bukkit.getPluginManager().registerEvents(this.serverPingListener, this);
        Bukkit.getPluginManager().registerEvents(this.pointsChangeListener, this);
        Bukkit.getWorld("world").setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, Boolean.valueOf(false));
        Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, Boolean.valueOf(false));
        Bukkit.getWorld("world").setGameRule(GameRule.DO_WEATHER_CYCLE, Boolean.valueOf(false));
        Bukkit.getWorld("world").setTime(5000L);
    }
}
