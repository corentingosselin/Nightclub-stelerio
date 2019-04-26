package com.stelerio.plugin.nightclub;

import org.bukkit.plugin.java.JavaPlugin;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.stelerio.plugin.nightclub.systems.MovementSystem;

public class NightClub extends JavaPlugin {
    private World mWorld;
    private NightClubRunnable mNightClubRunnable;
    
    @Override
    public void onEnable() {
        WorldConfiguration worldConfig = new WorldConfigurationBuilder()
                /* Register systems and plugins */
                .with(new MovementSystem())
                .build();
        mWorld = new World(worldConfig);
        
        mNightClubRunnable = new NightClubRunnable(mWorld);
        mNightClubRunnable.runTaskAsynchronously(this);
    }
    
    @Override
    public void onDisable() {
        mNightClubRunnable.cancel();
    }
}
