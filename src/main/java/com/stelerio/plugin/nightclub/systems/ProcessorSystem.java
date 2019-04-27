package com.stelerio.plugin.nightclub.systems;

import java.util.Queue;

import com.artemis.BaseSystem;
import com.stelerio.plugin.nightclub.processors.Processor;

public class ProcessorSystem extends BaseSystem {
    private final Queue<Processor> mAwaitingProcessors;
    
    public ProcessorSystem(Queue<Processor> awaitingProcessors) {
        mAwaitingProcessors = awaitingProcessors;
    }
    
    @Override
    protected void processSystem() {
        Processor processor;
        while ((processor = mAwaitingProcessors.poll()) != null) processor.execute(world);
    }

}
