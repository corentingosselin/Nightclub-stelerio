package com.stelerio.plugin.nightclub.packet.entity;

import com.stelerio.plugin.nightclub.packet.WrappedEntity;

public class WrapperEntityGuardian extends WrappedEntity {


    public WrapperEntityGuardian(double x, double y, double z, int id) {
        super(x,y,z,0,0,id, 28);
    }


    public void setTarget(int targetID) {
        setDataWatcherObject(Integer.class,13,targetID);
    }


}
