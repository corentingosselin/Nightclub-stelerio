package com.stelerio.plugin.nightclub.processors;

import java.util.UUID;

import org.bukkit.Location;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.ArchetypeManager.ArchetypeType;
import com.stelerio.plugin.nightclub.components.ProjectorSynchronizationComponent;
import com.stelerio.plugin.nightclub.components.TransformComponent;

public class CreateProjectorProcessor extends Processor {
    private final Location mLocation;
    private final UUID mUUID;
    
    public CreateProjectorProcessor(UUID uuid, Location location) {
        mLocation = location;
        mUUID = uuid;
    }
    
    @Override
    public void execute(World world, ArchetypeManager archetypeManager) {
        Entity e = world.createEntity(archetypeManager.getArchetype(ArchetypeType.PROJECTOR));
        TransformComponent transform = world.getMapper(TransformComponent.class).get(e);
        transform.position.set((float) mLocation.getX(), (float) mLocation.getY(), (float) mLocation.getZ());
        transform.rotation.set(0, mLocation.getPitch(), mLocation.getYaw());
        world.getSystem(UuidEntityManager.class).setUuid(e, mUUID);
    }

}
