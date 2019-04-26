package com.stelerio.plugin.nightclub.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import com.stelerio.plugin.nightclub.components.VelocityComponent;

public class MovementSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;
    
    public MovementSystem() {
        super(Aspect.all(TransformComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(int entityId) {
        TransformComponent transform = mTransform.get(entityId);
        VelocityComponent velocity = mVelocity.get(entityId);
        
        transform.getPosition().set(
            transform.getPosition().getX() + velocity.getPositionVelocity().getX() * world.getDelta(),
            transform.getPosition().getY() + velocity.getPositionVelocity().getY() * world.getDelta(),
            transform.getPosition().getZ() + velocity.getPositionVelocity().getZ() * world.getDelta()
        );
        
        transform.getRotation().set(
            transform.getRotation().getX() + velocity.getRotationVelocity().getX() * world.getDelta(), 
            transform.getRotation().getY() + velocity.getRotationVelocity().getY() * world.getDelta(), 
            transform.getRotation().getZ() + velocity.getRotationVelocity().getZ() * world.getDelta()
        );
    }

}
