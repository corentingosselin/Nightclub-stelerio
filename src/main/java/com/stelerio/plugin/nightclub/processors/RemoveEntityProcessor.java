package com.stelerio.plugin.nightclub.processors;

import java.util.UUID;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.stelerio.plugin.nightclub.ArchetypeManager;

public class RemoveEntityProcessor extends Processor {
    private final UUID mUUID;
    
    public RemoveEntityProcessor(UUID uuid) {
        mUUID = uuid;
    }
    
    @Override
    public void execute(World world, ArchetypeManager archetypeManager) {
        Entity e = world.getSystem(UuidEntityManager.class).getEntity(mUUID);
        if (e != null) world.deleteEntity(e);
    }

}
