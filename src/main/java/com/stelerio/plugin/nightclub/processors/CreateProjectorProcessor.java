package com.stelerio.plugin.nightclub.processors;

import org.bukkit.Location;

import com.artemis.World;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.ArchetypeManager.ArchetypeType;
import com.stelerio.plugin.nightclub.components.TransformComponent;

public class CreateProjectorProcessor extends Processor {
    private Location mLocation;
    
    public CreateProjectorProcessor(Location location) {
        mLocation = location;
    }
    
    @Override
    public void execute(World world, ArchetypeManager archetypeManager) {
        int e = world.create(archetypeManager.getArchetype(ArchetypeType.PROJECTOR));
        TransformComponent transform = world.getMapper(TransformComponent.class).get(e);
        transform.position.set((float) mLocation.getX(), (float) mLocation.getY(), (float) mLocation.getZ());
        transform.rotation.set(0, mLocation.getPitch(), mLocation.getYaw());
    }

}
