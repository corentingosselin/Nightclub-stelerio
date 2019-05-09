package com.stelerio.plugin.nightclub.utils;

public class Vector3f {
    public static final Vector3f ZERO = new Vector3f();
    
    protected float mX;
    protected float mY;
    protected float mZ;
    
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

    public Vector3f add(Vector3f other) {
        mX = mX + other.getX();
        mY = mY + other.getY();
        mZ = mZ + other.getZ();
        return this;
    }

    public Vector3f clone() {
        return new Vector3f(mX,mY,mZ);
    }
    
    public boolean equals(Vector3f other) {
        if (this == other) return true;
        return other.getX() == getX() && other.getY() == getY() && other.getZ() == getZ();
    }
    
    public double distance(Vector3f other) {
        return Math.sqrt(Math.pow(other.getX() - getX(), 2) + Math.pow(other.getY() - getY(), 2) + Math.pow(other.getZ() - getZ(), 2));
    }
}
