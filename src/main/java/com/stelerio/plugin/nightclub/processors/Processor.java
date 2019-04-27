package com.stelerio.plugin.nightclub.processors;

import com.artemis.World;
import com.stelerio.plugin.nightclub.ArchetypeManager;

public abstract class Processor {
    public abstract void execute(World world, ArchetypeManager archetypeManager);
}
