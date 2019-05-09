package com.stelerio.plugin.nightclub.packet;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.stelerio.plugin.nightclub.packet.wrapper.*;
import com.stelerio.plugin.nightclub.utils.FastTrig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class WrappedEntity {


    private WrapperPlayServerSpawnEntityLiving spawnPacket;
    private WrapperPlayServerEntityDestroy destroyPacket;
    private WrapperPlayServerEntityMetadata metaPacket;
    private WrapperPlayServerEntityEquipment equiPacket;
    private WrapperPlayServerEntityTeleport teleportPacket;
    private WrapperPlayServerEntityHeadRotation yawPacket;

    private WrappedDataWatcher dataWatcher;

    private double x,y,z;
    private float yaw,pitch;
    /**
     * @check https://wiki.vg/Entity_metadata#Mobs
     */
    private int typeID;
    private int id;

    public WrappedEntity(double x, double y, double z, float yaw, float pitch, int id, int typeID) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.typeID = typeID;

        spawnPacket = new WrapperPlayServerSpawnEntityLiving();
        spawnPacket.setEntityID(id);
        spawnPacket.setType(typeID);
        spawnPacket.setPitch(pitch);
        spawnPacket.setHeadPitch(pitch);
        spawnPacket.setYaw(yaw);
        spawnPacket.setX(x);
        spawnPacket.setY(y);
        spawnPacket.setZ(z);

        yawPacket = new WrapperPlayServerEntityHeadRotation();
        yawPacket.setEntityID(id);
        yawPacket.setHeadYaw(FastTrig.toPackedByte(yaw));

        destroyPacket = new WrapperPlayServerEntityDestroy();
        destroyPacket.setEntityIds(new int[] {id});

        metaPacket = new WrapperPlayServerEntityMetadata();

        teleportPacket = new WrapperPlayServerEntityTeleport();

        equiPacket = new WrapperPlayServerEntityEquipment();
        equiPacket.setEntityID(id);

        dataWatcher = new WrappedDataWatcher();
    }

    public WrappedEntity(double x, double y, double z, float yaw, float pitch, int typeID) {
        this(x,y,z,yaw,pitch,EIDGen.generateEID(),typeID);
    }

    public WrappedEntity(double x, double y, double z, int typeID) {
        this(x,y,z,0,0,EIDGen.generateEID(),typeID);
    }

    public void teleport(double x, double y, double z) {
        teleportPacket.setEntityID(id);
        teleportPacket.setOnGround(false);
        teleportPacket.setX(x);
        teleportPacket.setY(y);
        teleportPacket.setZ(z);
        teleportPacket.broadcastPacket();
        this.x = x;
        this.y = y;
        this.z = z;

        //update spawn location too
        spawnPacket.setX(x);
        spawnPacket.setY(y);
        spawnPacket.setZ(z);

    }

    public void spawnClient(Player client) {
        spawnPacket.sendPacket(client);
        //armorstand does not need it...
        if(id != 1)
            yawPacket.sendPacket(client);
    }

    public void updateForClient(Player client) {

    }

    public void setDataWatcherObject(Class<?> type, int objectIndex, Object object) {
        WrappedDataWatcher.WrappedDataWatcherObject watcherObject = new WrappedDataWatcher.WrappedDataWatcherObject(objectIndex, WrappedDataWatcher.Registry.get(type));
        dataWatcher.setObject(watcherObject,object);
    }


    public void sendUpdatedmetatada() {
        metaPacket.setMetadata(dataWatcher.getWatchableObjects());
        metaPacket.broadcastPacket();
    }


    public void despawnClient(Player client) {
        destroyPacket.sendPacket(client);

    }

    public void spawn() {
        spawnPacket.setMetadata(dataWatcher);
        spawnPacket.broadcastPacket();
        //armorstand does not need it
        if(id != 1)
            yawPacket.broadcastPacket();
    }

    public void despawn() {
        destroyPacket.broadcastPacket();
    }

    public void equip(EnumWrappers.ItemSlot slot, ItemStack item) {
        equiPacket.setItem(item);
        equiPacket.setSlot(slot);
        equiPacket.broadcastPacket();
    }

    public void setInvisible(boolean invisible) {
        setDataWatcherObject(Byte.class,0,invisible ? (byte) 0x20 : (byte) 0);
    }

    public WrappedDataWatcher getDataWatcher() {
        return dataWatcher;
    }

    public int getId() {
        return id;
    }

}
