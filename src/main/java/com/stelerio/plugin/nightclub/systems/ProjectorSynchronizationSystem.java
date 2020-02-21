package com.stelerio.plugin.nightclub.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.stelerio.plugin.nightclub.components.ProjectorComponent;
import com.stelerio.plugin.nightclub.components.ProjectorSynchronizationComponent;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import com.stelerio.plugin.nightclub.components.VelocityComponent;
import com.stelerio.plugin.nightclub.utils.Vector3f;

public class ProjectorSynchronizationSystem extends IteratingSystem {


    private ComponentMapper<ProjectorSynchronizationComponent> mProjectorSynchronization;
    private ComponentMapper<ProjectorSynchronizationComponent> mProjector;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;

    public ProjectorSynchronizationSystem() {
        super(Aspect.all(TransformComponent.class, ProjectorSynchronizationComponent.class, VelocityComponent.class, ProjectorComponent.class));
    }

    @Override
    protected void process(int entityId) {
        ProjectorSynchronizationComponent projectorSync = mProjectorSynchronization.get(entityId);
        TransformComponent transform = mTransform.get(entityId);
        //velocity check is in movement
        VelocityComponent velocity = mVelocity.get(entityId);
        ProjectorSynchronizationComponent projector = mProjector.get(entityId);


        //check if the pos is the same

        // check if position has changed
        if (velocity.positionVelocity != null) {


        }

        // check if rotation has changed
        if (transform.rotation != null) {
            if(velocity.rotationVelocity.equals(Vector3f.ZERO)) {

            }
        }


        //TODO I can't send the packet because I do not have the location, or I need a method to calculate distance between 2 points
        //PacketUtil.sendGlobalPacket(location,tp);

        //TODO same here, I need a vector or pitch/yaw to change the projector's looking direction
        //+ move yaw and pitch of the projector
        //entityLook packet for yaw
        //set euler angle x + metadata packet for pitch
    }

}
