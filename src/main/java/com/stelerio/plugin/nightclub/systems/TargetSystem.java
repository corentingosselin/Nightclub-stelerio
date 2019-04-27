package com.stelerio.plugin.nightclub.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.stelerio.plugin.nightclub.components.TargetComponent;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import com.stelerio.plugin.nightclub.components.VelocityComponent;
import com.stelerio.plugin.nightclub.utils.Vector3f;

public class TargetSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<VelocityComponent> mVelocity;
    private ComponentMapper<TargetComponent> mTarget;

    @SuppressWarnings("unchecked")
    public TargetSystem() {
        super(Aspect.all(TransformComponent.class, VelocityComponent.class, TargetComponent.class));
    }
    
    @Override
    protected void process(int entityId) {
        VelocityComponent velocity = mVelocity.get(entityId);
        TransformComponent transform = mTransform.get(entityId);
        TargetComponent target = mTarget.get(entityId);
        
        // Position target interpolation
        if (target.position != null) {
            if (target.position.equals(transform.position)) {
                velocity.positionVelocity.set(0, 0, 0);
                target.position = null;
            } else if (velocity.positionVelocity.equals(Vector3f.ZERO)) { // Don't set the velocity if it's already set.
                velocity.positionVelocity.set(
                    (target.position.getX() - transform.position.getX()) / target.positionTime,
                    (target.position.getY() - transform.position.getY()) / target.positionTime,
                    (target.position.getZ() - transform.position.getZ()) / target.positionTime
                );
            }
        }
        
        // Rotation target interpolation
        if (target.rotation != null) {
            if (target.rotation.equals(transform.rotation)) {
                velocity.rotationVelocity.set(0, 0, 0);
                target.rotation = null;
            } else if (velocity.rotationVelocity.equals(Vector3f.ZERO)) { // Don't set the velocity if it's already set.
                velocity.rotationVelocity.set(
                    (target.rotation.getX() - transform.rotation.getX()) / target.rotationTime,
                    (target.rotation.getY() - transform.rotation.getY()) / target.rotationTime,
                    (target.rotation.getZ() - transform.rotation.getZ()) / target.rotationTime
                );
            }
        }
    }

}
