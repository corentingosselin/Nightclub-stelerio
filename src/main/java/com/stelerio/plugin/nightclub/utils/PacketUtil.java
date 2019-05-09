package com.stelerio.plugin.nightclub.utils;

import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.stelerio.plugin.nightclub.packet.AbstractPacket;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Guardian;

public class PacketUtil {

    public static final int viewDistance = Bukkit.getViewDistance();

    /**
     * Send packet only for player who can view
     * //TODO Check if location is really useful, because it seems even if you send packet for everyplayer, the packet is viewable for everyone inside a map.
     * //Moreover if the nightclub is size limited, we do not need to check the distance radius
     * @param packet
     */
    public static void sendGlobalPacket(AbstractPacket packet) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            packet.sendPacket(player);
        });
    }

    public static void sendGlobalPacket(AbstractPacket... packet) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            for (AbstractPacket packetContainer : packet) {
                packetContainer.sendPacket(player);
            }
        });
    }


    private static ArmorStand fakeArmorStand;
    private static Guardian fakeGuardian;
    static {
        fakeArmorStand = (ArmorStand) Accessors.getConstructorAccessor(
                MinecraftReflection.getCraftBukkitClass("entity.CraftArmorStand"),
                MinecraftReflection.getCraftBukkitClass("CraftServer"),
                MinecraftReflection.getMinecraftClass("EntityArmorStand")
        ).invoke(null, Accessors.getConstructorAccessor(
                MinecraftReflection.getMinecraftClass("EntityArmorStand"),
                MinecraftReflection.getNmsWorldClass()
        ).invoke(new Object[]{null}));

        fakeGuardian = (Guardian) Accessors.getConstructorAccessor(
                MinecraftReflection.getCraftBukkitClass("entity.CraftGuardian"),
                MinecraftReflection.getCraftBukkitClass("CraftServer"),
                MinecraftReflection.getMinecraftClass("EntityGuardian")
        ).invoke(null, Accessors.getConstructorAccessor(
                MinecraftReflection.getMinecraftClass("EntityGuardian"),
                MinecraftReflection.getNmsWorldClass()
        ).invoke(new Object[] {null}));

    }

    public static ArmorStand getFakeArmorStand() {
        return fakeArmorStand;
    }

    public static Guardian getFakeGuardian() {
        return fakeGuardian;
    }
}
