package com.stelerio.plugin.nightclub.systems;

import java.util.Queue;

import com.artemis.BaseSystem;
import com.stelerio.plugin.nightclub.ArchetypeManager;
import com.stelerio.plugin.nightclub.processors.Processor;

public class ProcessorSystem extends BaseSystem {
    private final Queue<Processor> mAwaitingProcessors;
    private ArchetypeManager mArchetypeManager;
    
    public ProcessorSystem(Queue<Processor> awaitingProcessors) {
        mAwaitingProcessors = awaitingProcessors;
    }
    
    public void setArchetypeManager(ArchetypeManager archetypeManager) {
        mArchetypeManager = archetypeManager;
    }
    
    @Override
    protected void processSystem() {
        Processor processor;
        while ((processor = mAwaitingProcessors.poll()) != null) processor.execute(world, mArchetypeManager);
    }

}
