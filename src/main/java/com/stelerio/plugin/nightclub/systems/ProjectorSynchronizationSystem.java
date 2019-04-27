package com.stelerio.plugin.nightclub.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.stelerio.plugin.nightclub.components.ProjectorSynchronizationComponent;
import com.stelerio.plugin.nightclub.components.TransformComponent;

public class ProjectorSynchronizationSystem extends IteratingSystem {
    private ComponentMapper<ProjectorSynchronizationComponent> mProjectorSynchronization;
    private ComponentMapper<TransformComponent> mTransform;

    public ProjectorSynchronizationSystem() {
        super(Aspect.all(TransformComponent.class, ProjectorSynchronizationComponent.class));
    }
    
    @Override
    protected void process(int entityId) {
        ProjectorSynchronizationComponent projectorSync = mProjectorSynchronization.get(entityId);
        TransformComponent transform = mTransform.get(entityId);
        
        // TODO send packets depending on projector data
    }

}
