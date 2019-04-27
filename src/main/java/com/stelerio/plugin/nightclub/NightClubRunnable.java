package com.stelerio.plugin.nightclub;

import org.bukkit.scheduler.BukkitRunnable;

import com.artemis.World;

public class NightClubRunnable extends BukkitRunnable {
    private final static long MS_PER_UPDATE_TICK = 50; // ~20 hz
    private final World mWorld;
    
    public NightClubRunnable(World world) {
        mWorld = world;
    }

    public void run() {
        long currTime = System.currentTimeMillis();
        long delta;
        float lag = 0;
        while (!isCancelled()) {
            long prevTime = currTime;
            currTime = System.currentTimeMillis();
            delta = currTime - prevTime;
            
            if (delta > 0) {
                lag += delta;
                while (lag > MS_PER_UPDATE_TICK) {
                    mWorld.setDelta(MS_PER_UPDATE_TICK);
                    mWorld.process();
                }
            }
            
            if (delta < MS_PER_UPDATE_TICK) {
                try {Thread.sleep(MS_PER_UPDATE_TICK - delta);} catch (InterruptedException e) {/* THREAD INTERRUPTED*/}
            }
        }
    }

}
