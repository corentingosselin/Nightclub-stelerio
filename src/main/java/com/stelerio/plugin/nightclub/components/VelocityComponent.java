package com.stelerio.plugin.nightclub.components;

import com.artemis.Component;
import com.stelerio.plugin.nightclub.utils.Vector3f;

public class VelocityComponent extends Component {
    private Vector3f mPositionVelocity;
    private Vector3f mRotationVelocity;
    
    public VelocityComponent() {}
    
    public VelocityComponent(Vector3f positionVelocity, Vector3f rotationVelocity) {
        mPositionVelocity = positionVelocity;
        mRotationVelocity = rotationVelocity;
    }
    
    public void setRotationVelocity(Vector3f rotationVelocity) {
        mRotationVelocity = rotationVelocity;
    }
    
    public void setPositionVelocity(Vector3f positionVelocity) {
        mPositionVelocity = positionVelocity;
    }
    
    public Vector3f getPositionVelocity() {
        return mPositionVelocity;
    }
    
    public Vector3f getRotationVelocity() {
        return mRotationVelocity;
    }
}
