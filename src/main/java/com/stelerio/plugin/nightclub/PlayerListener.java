package com.stelerio.plugin.nightclub;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.stelerio.plugin.nightclub.processors.SynchronizePlayerProcessor;

public class PlayerListener implements Listener {
    private final NightClub mPlugin;
    
    public PlayerListener(NightClub plugin) {
        mPlugin = plugin;
    }
    
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        mPlugin.queueProcessor(new SynchronizePlayerProcessor(event.getPlayer()));
    }
}
