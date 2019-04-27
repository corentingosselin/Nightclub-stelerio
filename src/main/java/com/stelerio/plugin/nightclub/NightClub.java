package com.stelerio.plugin.nightclub;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.plugin.java.JavaPlugin;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.utils.IntBag;
import com.stelerio.plugin.nightclub.processors.Processor;
import com.stelerio.plugin.nightclub.systems.ProcessorSystem;
import com.stelerio.plugin.nightclub.systems.ProjectorSynchronizationSystem;
import com.stelerio.plugin.nightclub.systems.TargetSystem;
import com.stelerio.plugin.nightclub.systems.VelocitySystem;

public class NightClub extends JavaPlugin {
    private World mWorld;
    private NightClubRunnable mNightClubRunnable;
    private Queue<Processor> mProcessorQueue = new ConcurrentLinkedQueue<Processor>();
    
    @Override
    public void onEnable() {
        WorldConfiguration worldConfig = new WorldConfigurationBuilder()
                /* Register systems and plugins */
                .with(new ProcessorSystem(mProcessorQueue))
                .with(new TargetSystem())
                .with(new VelocitySystem())
                .with(new ProjectorSynchronizationSystem())
                .build();
        mWorld = new World(worldConfig);
        
        mNightClubRunnable = new NightClubRunnable(mWorld);
        mNightClubRunnable.runTaskAsynchronously(this);
    }
    
    public void queueProcessor(Processor processor) {
        mProcessorQueue.offer(processor);
    }
    
    @Override
    public void onDisable() {
        mNightClubRunnable.cancel();
        IntBag bag = mWorld.getAspectSubscriptionManager().get(Aspect.all()).getEntities();
        for (int i : bag.getData()) mWorld.delete(i);
    }
}
