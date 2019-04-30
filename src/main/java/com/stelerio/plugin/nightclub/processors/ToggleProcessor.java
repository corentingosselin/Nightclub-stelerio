package com.stelerio.plugin.nightclub.processors;

import java.util.UUID;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.components.ToggleComponent;

public class ToggleProcessor extends Processor {
    private final UUID mUUID;
    private final boolean mStatus;
    
    public ToggleProcessor(UUID uuid, boolean status) {
        mUUID = uuid;
        mStatus = status;
    }
    
    @Override
    public void execute(World world, ArchetypeManager archetypeManager) {
        Entity e = world.getSystem(UuidEntityManager.class).getEntity(mUUID);
        if (e != null) {
            ToggleComponent component = e.getComponent(ToggleComponent.class);
            if (component != null) {
                component.status = mStatus;
                // We can use this part to send an event
            }
        }
    }
}