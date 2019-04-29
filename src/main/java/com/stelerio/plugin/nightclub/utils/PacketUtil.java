package com.stelerio.plugin.nightclub.utils;

import com.stelerio.plugin.nightclub.packet.AbstractPacket;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PacketUtil {

    private static final int viewDistance = Bukkit.getViewDistance();

    /**
     * Send packet only for player who can view
     * //TODO Check if location is really useful, because it seems even if you send packet for everyplayer, the packet is viewable for everyone inside a map.
     * //Moreover if the nightclub is size limited, we do not need to check the distance radius
     * @param loc
     * @param packet
     */
    public static void sendGlobalPacket(Location loc, AbstractPacket packet) {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getLocation().distanceSquared(loc) <= viewDistance).forEach(player -> {
            packet.sendPacket(player);
        });
    }

    public static void sendGlobalPacket(Location loc,AbstractPacket... packet) {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getLocation().distanceSquared(loc) <= viewDistance).forEach(player -> {
            for (AbstractPacket packetContainer : packet) {
                packetContainer.sendPacket(player);
            }
        });
    }
}
