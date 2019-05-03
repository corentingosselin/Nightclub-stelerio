package com.stelerio.plugin.nightclub.processors;

import java.util.UUID;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.components.TargetComponent;
import com.stelerio.plugin.nightclub.utils.Vector3f;

public class TargetUpdateProcessor extends Processor {
    private final UUID mUUID;
    private final Vector3f mPosition;
    private final Vector3f mRotation;
    private final long mPositionTime;
    private final long mRotationTime;
    
    public TargetUpdateProcessor(UUID uuid, Vector3f position, long positionTime, Vector3f rotation, long rotationTime) {
        mUUID = uuid;
        mPosition = position;
        mPositionTime = positionTime;
        mRotation = rotation;
        mRotationTime = rotationTime;
    }

    @Override
    public void execute(World world, ArchetypeManager archetypeManager) {
        Entity e = world.getSystem(UuidEntityManager.class).getEntity(mUUID);
        if (e != null) {
            TargetComponent target = e.getComponent(TargetComponent.class);
            target.position = mPosition;
            target.positionTime = mPositionTime;
            target.rotation = mRotation;
            target.rotationTime = mRotationTime;
        }
    }

}
