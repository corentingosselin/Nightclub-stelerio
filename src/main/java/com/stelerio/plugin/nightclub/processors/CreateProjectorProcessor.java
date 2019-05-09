package com.stelerio.plugin.nightclub.processors;

import java.util.UUID;

import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.stelerio.plugin.nightclub.packet.EIDGen;
import com.stelerio.plugin.nightclub.packet.wrapper.WrapperPlayServerEntityEquipment;
import com.stelerio.plugin.nightclub.packet.wrapper.WrapperPlayServerSpawnEntityLiving;
import com.stelerio.plugin.nightclub.utils.PacketUtil;
import com.stelerio.plugin.nightclub.utils.ProjectorItemState;
import org.bukkit.Location;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.ArchetypeManager.ArchetypeType;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import org.bukkit.entity.ArmorStand;

public class CreateProjectorProcessor extends Processor {
    private final Location mLocation;
    private final UUID mUUID;
    private final int mProjectorID;

    //I use this datawatcher to not recreate a watcher each time I am updating projector armorstand metadata
    private WrappedDataWatcher projectorDataWatcher;
    private static ArmorStand fakeArmorStand;

    static {
        fakeArmorStand = (ArmorStand) Accessors.getConstructorAccessor(
                MinecraftReflection.getCraftBukkitClass("entity.CraftArmorStand"),
                MinecraftReflection.getCraftBukkitClass("CraftServer"),
                MinecraftReflection.getMinecraftClass("EntityArmorStand")
        ).invoke(null, Accessors.getConstructorAccessor(
                MinecraftReflection.getMinecraftClass("EntityArmorStand"),
                MinecraftReflection.getNmsWorldClass()
        ).invoke(new Object[]{null}));

    }

    public CreateProjectorProcessor(UUID uuid, Location location) {
        mLocation = location;
        mUUID = uuid;
        mProjectorID = EIDGen.generateEID();

    }

    /**
     * Spawn projector at the exact location where admin is standing + yaw and pitch rotation.
     * Don't call this method inside join event, async join event instead.
     * Moreover TODO listen map chunk packet to display the projector, this would do the job without async player join (be sure that the packet is not sent twice without the previous entity removing).
     * @param world
     * @param archetypeManager
     */
    @Override
    public void execute(World world, ArchetypeManager archetypeManager) {
        Entity e = world.createEntity(archetypeManager.getArchetype(ArchetypeType.PROJECTOR));
        TransformComponent transform = world.getMapper(TransformComponent.class).get(e);
        transform.position.set((float) mLocation.getX(), (float) mLocation.getY(), (float) mLocation.getZ());
        transform.rotation.set(0, mLocation.getPitch(), mLocation.getYaw());
        world.getSystem(UuidEntityManager.class).setUuid(e, mUUID);
        ProjectorSynchronizationComponent sync = world.getMapper(ProjectorSynchronizationComponent.class).get(e);
        sync.projectorID = mProjectorID;

        projectorDataWatcher = WrappedDataWatcher.getEntityWatcher(fakeArmorStand);
        projectorDataWatcher.setObject(0,(byte)0x20);
        //set Head Pitch
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getVectorSerializer();
        projectorDataWatcher.setObject(12,serializer,new Vector3F(mLocation.getPitch(),0,0));
        sync.projectorDatawatcher = projectorDataWatcher;


        WrapperPlayServerSpawnEntityLiving spawn = new WrapperPlayServerSpawnEntityLiving(fakeArmorStand);
        spawn.setEntityID(mProjectorID);
        //I am setting the same uuid, maybe useful for future
        spawn.setUniqueId(mUUID);
        spawn.setHeadPitch(mLocation.getPitch());
        spawn.setYaw(mLocation.getYaw());
        spawn.setMetadata(projectorDataWatcher);
        spawn.setX(mLocation.getX());
        spawn.setY(mLocation.getY());
        spawn.setZ(mLocation.getZ());

        PacketUtil.sendGlobalPacket(mLocation,spawn);

        //then sent entity equipment
        WrapperPlayServerEntityEquipment equip = new WrapperPlayServerEntityEquipment();
        equip.setEntityID(mProjectorID);
        equip.setItem(ProjectorItemState.OFF.getHead());
        equip.setSlot(EnumWrappers.ItemSlot.HEAD);
        PacketUtil.sendGlobalPacket(mLocation,equip);


    }

}
