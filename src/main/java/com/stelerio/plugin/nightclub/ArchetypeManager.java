package com.stelerio.plugin.nightclub;

import java.util.EnumMap;
import java.util.Map;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;
import com.stelerio.plugin.nightclub.components.*;

public class ArchetypeManager {
    private Map<ArchetypeType, Archetype> mArchetypes = new EnumMap<ArchetypeType, Archetype>(ArchetypeType.class);

    public ArchetypeManager(World world) {
        mArchetypes.put(ArchetypeType.PROJECTOR,
                new ArchetypeBuilder()
                        .add(TransformComponent.class)
                        .add(ColorComponent.class)
                        .add(VelocityComponent.class)
                        .add(TargetComponent.class)
                        .add(ProjectorComponent.class)
                        .add(LaserComponent.class)
                        .add(ToggleComponent.class)
                        .add(ProjectorSynchronizationComponent.class)
                        .build(world)
        );
    }

    public Archetype getArchetype(ArchetypeType type) {
        return mArchetypes.get(type);
    }

    public enum ArchetypeType {
        PROJECTOR
        /* TODO add others */
    }
}
