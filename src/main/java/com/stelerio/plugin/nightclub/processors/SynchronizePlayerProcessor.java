package com.stelerio.plugin.nightclub.processors;

import org.bukkit.entity.Player;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.components.SynchronizationComponent;

public class SynchronizePlayerProcessor extends Processor {
    private final Player mPlayer;
    
    public SynchronizePlayerProcessor(Player player) {
        mPlayer = player;
    }
    
    @Override
    public void execute(World world, ArchetypeManager archetypeManager) {
        IntBag entities = world.getAspectSubscriptionManager().get(Aspect.all(SynchronizationComponent.class)).getEntities();
        // TODO Send first sync when player joins
    }

}
