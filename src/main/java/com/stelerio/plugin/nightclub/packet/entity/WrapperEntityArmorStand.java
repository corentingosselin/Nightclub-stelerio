package com.stelerio.plugin.nightclub.packet.entity;

import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.stelerio.plugin.nightclub.packet.WrappedEntity;

public class WrapperEntityArmorStand extends WrappedEntity {

    public WrapperEntityArmorStand(double x, double y, double z,float yaw , float pitch, int id) {
        super(x,y,z,yaw,pitch,id, 1);
    }


    public WrapperEntityArmorStand(double x, double y, double z, int id) {
        super(x,y,z,0,0,id, 1);
    }


    /**
     * Parameters in degree
     * @param x
     * @param y
     * @param z
     */
    public void setHeadPose(float x, float y, float z) {
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getVectorSerializer();
        //No confusion with vector3f from this plugin
        getDataWatcher().setObject(12,serializer,new Vector3F(x,y,z));
    }

}
