package com.stelerio.plugin.nightclub.processors;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.ArchetypeManager.ArchetypeType;
import com.stelerio.plugin.nightclub.components.ProjectorSynchronizationComponent;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import com.stelerio.plugin.nightclub.packet.EIDGen;
import com.stelerio.plugin.nightclub.packet.entity.WrapperEntityArmorStand;
import com.stelerio.plugin.nightclub.utils.ProjectorItemState;
import org.bukkit.Location;

import java.util.UUID;

public class CreateProjectorProcessor extends Processor {
    private final Location mLocation;
    private final UUID mUUID;
    private final int mProjectorID;


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
        transform.rotation.set(mLocation.getYaw(), mLocation.getPitch(), 0);
        world.getSystem(UuidEntityManager.class).setUuid(e, mUUID);
        ProjectorSynchronizationComponent sync = world.getMapper(ProjectorSynchronizationComponent.class).get(e);
        sync.networkId = mProjectorID;

        WrapperEntityArmorStand projectorArmorStand = new WrapperEntityArmorStand(
                mLocation.getX(),
                mLocation.getY(),
                mLocation.getZ(),
                mLocation.getYaw(),
                mLocation.getPitch(),
                mProjectorID
        );
        projectorArmorStand.setInvisible(true);
        projectorArmorStand.setHeadPose(mLocation.getPitch(),0,0);
        projectorArmorStand.spawn();
        projectorArmorStand.equip(
                EnumWrappers.ItemSlot.HEAD,
                ProjectorItemState.OFF.getHead()
        );






    }

}
