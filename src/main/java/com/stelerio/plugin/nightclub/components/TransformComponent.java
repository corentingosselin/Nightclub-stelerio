package com.stelerio.plugin.nightclub.components;

import com.artemis.Component;
import com.stelerio.plugin.nightclub.utils.Vector3f;

public class TransformComponent extends Component {
    private Vector3f mPosition;
    private Vector3f mRotation;
    private Vector3f mScale;
    
    public TransformComponent() {}
    
    public TransformComponent(Vector3f position, Vector3f rotation, Vector3f scale) {
        mPosition = position;
        mRotation = rotation;
        mScale = scale;
    }
    
    public void setRotation(Vector3f rotation) {
        mRotation = rotation;
    }
    
    public void setPosition(Vector3f position) {
        mPosition = position;
    }
    
    public void setSclae(Vector3f scale) {
        mScale = scale;
    }
    
    public Vector3f getPosition() {
        return mPosition;
    }
    
    public Vector3f getRotation() {
        return mRotation;
    }
    
    public Vector3f getScale() {
        return mScale;
    }
}
