package com.stelerio.plugin.nightclub.processors;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.components.LaserComponent;
import com.stelerio.plugin.nightclub.components.ProjectorSynchronizationComponent;
import com.stelerio.plugin.nightclub.components.ToggleComponent;
import com.stelerio.plugin.nightclub.components.TransformComponent;
import com.stelerio.plugin.nightclub.packet.EIDGen;
import com.stelerio.plugin.nightclub.packet.entity.WrapperEntityArmorStand;
import com.stelerio.plugin.nightclub.packet.entity.WrapperEntityGuardian;
import com.stelerio.plugin.nightclub.packet.wrapper.WrapperPlayServerEntityDestroy;
import com.stelerio.plugin.nightclub.packet.wrapper.WrapperPlayServerEntityEquipment;
import com.stelerio.plugin.nightclub.utils.FastTrig;
import com.stelerio.plugin.nightclub.utils.ProjectorItemState;
import com.stelerio.plugin.nightclub.utils.Vector3f;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ToggleProcessor extends Processor {
    private final UUID mUUID;
    private final boolean mStatus;

    public ToggleProcessor(UUID uuid, boolean status) {
        mUUID = uuid;
        mStatus = status;
    }

    @Override
    public void execute(World world, ArchetypeManager archetypeManager) {
        Entity e = world.getSystem(UuidEntityManager.class).getEntity(mUUID);
        if (e != null) {
            ToggleComponent component = e.getComponent(ToggleComponent.class);
            if (component != null) {

                //Well I think that's not a good idea here, because if we toggle 2 times with true
                //This will spawn 2 times a laser
                component.status = mStatus;

                //Is it better ?
                //if(mStatus.equals(component.status))
                  //  return;

                ProjectorSynchronizationComponent syncComponent = e.getComponent(ProjectorSynchronizationComponent.class);
                LaserComponent laserComponent = e.getComponent(LaserComponent.class);
                TransformComponent transformComponent = e.getComponent(TransformComponent.class);


                ItemStack head = mStatus ? ProjectorItemState.GREEN.getHead() : ProjectorItemState.OFF.getHead();

                WrapperPlayServerEntityEquipment equip = new WrapperPlayServerEntityEquipment();
                equip.setEntityID(syncComponent.networkId);
                equip.setItem(head);
                equip.setSlot(EnumWrappers.ItemSlot.HEAD);
                equip.broadcastPacket();


                if(mStatus) {
                    //we create a new fake laser (guardian + armorstand)
                    if (laserComponent.targetID == -1)
                        laserComponent.targetID = EIDGen.generateEID();
                    if(laserComponent.sourceID == -1)
                        laserComponent.sourceID = EIDGen.generateEID();


                    double rotX = transformComponent.rotation.getX(); //yaw
                    double rotY = transformComponent.rotation.getY(); // pitch
                    double xz = Math.cos(Math.toRadians(rotY));
                    Vector3f vector3f = new Vector3f();
                    vector3f.set(
                            (float) (-xz * FastTrig.sin(Math.toRadians(rotX))) * laserComponent.distance ,
                            (float) (-FastTrig.sin(Math.toRadians(rotY))) * laserComponent.distance  ,
                            (float) (xz * FastTrig.cos(Math.toRadians(rotX))) * laserComponent.distance
                    );

                    Vector3f targetPosition = transformComponent.position.clone().add(vector3f);
                    WrapperEntityArmorStand target = new WrapperEntityArmorStand(targetPosition.getX(),targetPosition.getY(),targetPosition.getZ(),laserComponent.targetID);
                    target.setInvisible(true);
                    target.spawn();


                    double x = transformComponent.position.getX();
                    double y = transformComponent.position.getY() + 1.4;
                    double z = transformComponent.position.getZ();
                    WrapperEntityGuardian source = new WrapperEntityGuardian(x,y,z,laserComponent.sourceID);
                    source.setInvisible(true);
                    source.setTarget(laserComponent.targetID);
                    source.spawn();

                } else {
                    //DESTROY
                    WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();

                    if (laserComponent.targetID != -1 && laserComponent.sourceID != -1)
                        destroy.setEntities(laserComponent.targetID,laserComponent.sourceID);
                    else if(laserComponent.targetID != -1)
                        destroy.setEntities(laserComponent.targetID);
                    else if(laserComponent.sourceID != -1)
                        destroy.setEntities(laserComponent.sourceID);
                    if(destroy.getCount() > 0)
                        destroy.broadcastPacket();


                }



            }

        }
    }
}