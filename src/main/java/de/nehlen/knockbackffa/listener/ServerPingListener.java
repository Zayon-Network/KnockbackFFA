package de.nehlen.knockbackffa.listener;

import de.nehlen.knockbackffa.KnockbackFFA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {

    private final KnockbackFFA knockbackFFA;

    public ServerPingListener(KnockbackFFA knockbackFFA) {
        this.knockbackFFA = knockbackFFA;
    }

    @EventHandler
    public void handleServerPing(ServerListPingEvent event) {
        event.setMotd(this.knockbackFFA.getGeneralConfig().getOrSetDefault("config.map.name", "MapName"));
    }
}
