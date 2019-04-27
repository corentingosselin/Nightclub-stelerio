package com.stelerio.plugin.nightclub.utils;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

public class PacketUtil {

    public static void sendGlobalPacket(PacketContainer packet) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            try {
                ProtocolLibrary.getProtocolManager()
                        .sendServerPacket(player, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public static void sendGlobalPacket(PacketContainer... packet) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            for (PacketContainer packetContainer : packet) {
                try {
                    ProtocolLibrary.getProtocolManager()
                            .sendServerPacket(player, packetContainer);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
