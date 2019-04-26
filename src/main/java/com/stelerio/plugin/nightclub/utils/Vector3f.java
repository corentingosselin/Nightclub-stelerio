package com.stelerio.plugin.nightclub.utils;

public class Vector3f {
    private float mX;
    private float mY;
    private float mZ;
    
    public Vector3f(float x, float y, float z) {
        mX = x;
        mY = y;
        mZ = z;
    }
    
    public Vector3f() {}
    
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
