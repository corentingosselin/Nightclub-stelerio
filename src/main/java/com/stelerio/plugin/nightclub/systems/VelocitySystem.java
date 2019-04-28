package com.stelerio.plugin.nightclub.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import com.stelerio.plugin.nightclub.components.VelocityComponent;

public class VelocitySystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;
    
    public VelocitySystem() {
        super(Aspect.all(TransformComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(int entityId) {
        TransformComponent transform = mTransform.get(entityId);
        VelocityComponent velocity = mVelocity.get(entityId);
        
        transform.position.set(
            transform.position.getX() + velocity.positionVelocity.getX() * world.getDelta(),
            transform.position.getY() + velocity.positionVelocity.getY() * world.getDelta(),
            transform.position.getZ() + velocity.positionVelocity.getZ() * world.getDelta()
        );
        
        transform.rotation.set(
            transform.rotation.getX() + velocity.rotationVelocity.getX() * world.getDelta(), 
            transform.rotation.getY() + velocity.rotationVelocity.getY() * world.getDelta(), 
            transform.rotation.getZ() + velocity.rotationVelocity.getZ() * world.getDelta()
        );
    }

}
