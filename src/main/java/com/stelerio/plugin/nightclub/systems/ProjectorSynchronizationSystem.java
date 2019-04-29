package com.stelerio.plugin.nightclub.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.stelerio.plugin.nightclub.components.ProjectorSynchronizationComponent;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import com.stelerio.plugin.nightclub.components.VelocityComponent;
import com.stelerio.plugin.nightclub.packet.wrapper.WrapperPlayServerEntityTeleport;
import com.stelerio.plugin.nightclub.utils.PacketUtil;
import com.stelerio.plugin.nightclub.utils.Vector3f;

public class ProjectorSynchronizationSystem extends IteratingSystem {


    private ComponentMapper<ProjectorSynchronizationComponent> mProjectorSynchronization;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;

    public ProjectorSynchronizationSystem() {
        super(Aspect.all(TransformComponent.class, ProjectorSynchronizationComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(int entityId) {
        ProjectorSynchronizationComponent projectorSync = mProjectorSynchronization.get(entityId);
        TransformComponent transform = mTransform.get(entityId);
        VelocityComponent velocity = mVelocity.get(entityId);
        // TODO send packets depending on projector data

        if (velocity.positionVelocity.equals(Vector3f.ZERO)) return;


        //let's tp the armorstand
        WrapperPlayServerEntityTeleport tp = new WrapperPlayServerEntityTeleport();
        tp.setEntityID(projectorSync.targetID);
        tp.setOnGround(false);
        tp.setX((double)transform.position.getX());
        tp.setY((double)transform.position.getY());
        tp.setZ((double)transform.position.getZ());
        //useless for armorstand
        tp.setPitch((byte) 0);
        tp.setYaw((byte) 0);

        //TODO I can't send the packet because I do not have the location, or I need a method to calculate distance between 2 points
        //PacketUtil.sendGlobalPacket(location,tp);

        //TODO same here, I need a vector or pitch/yaw to change the projector's looking direction
        //+ move yaw and pitch of the projector
        //entityLook packet for yaw
        //set euler angle x + metadata packet for pitch
    }

}
