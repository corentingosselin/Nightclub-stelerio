package com.stelerio.plugin.nightclub.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.stelerio.plugin.nightclub.components.ProjectorSynchronizationComponent;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import com.stelerio.plugin.nightclub.utils.PacketUtil;

public class ProjectorSynchronizationSystem extends IteratingSystem {
    private ComponentMapper<ProjectorSynchronizationComponent> mProjectorSynchronization;
    private ComponentMapper<TransformComponent> mTransform;



    @SuppressWarnings("unchecked")
    public ProjectorSynchronizationSystem() {
        super(Aspect.all(TransformComponent.class, ProjectorSynchronizationComponent.class));
    }
    
    @Override
    protected void process(int entityId) {
        ProjectorSynchronizationComponent projectorSync = mProjectorSynchronization.get(entityId);
        TransformComponent transform = mTransform.get(entityId);
        // TODO send packets depending on projector data

        /**
         * Packet Teleport
         *  int id
         *  double x, y,z
         *  byte yaw * 256.0f / 360.0f
         *  byte  pitch * 256.0f / 360.0f
         *  boolean g : on ground
         */
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
        packet.getIntegers().write(0,1);
        packet.getDoubles().write(0,1);
        packet.getDoubles().write(0,1);
        packet.getIntegers().write(0,1);
        PacketUtil.sendGlobalPacket(packet);
    }

}
