package net.voic3.Permissions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Nathan Rhodes
 * All rights reserved. © 2015.
 */
public class InjectEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        SettingsManager.getInstance().injectPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        SettingsManager.getInstance().uninjectPlayer(e.getPlayer());
    }
}