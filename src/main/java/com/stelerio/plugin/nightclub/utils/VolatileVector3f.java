package com.stelerio.plugin.nightclub.utils;

public class VolatileVector3f extends Vector3f {
    protected volatile float mX;
    protected volatile float mY;
    protected volatile float mZ;
    
    public VolatileVector3f(float x, float y, float z) {
        mX = x;
        mY = y;
        mZ = z;
    }
    
    public VolatileVector3f() {}
    
    public float getX() {
        return mX;
    }
    
    public float getY() {
        return mY;
    }
    
    public float getZ() {
        return mZ;
    }
    
    public void set(float x, float y, float z) {
        mX = x;
        mY = y;
        mZ = z;
    }
}
