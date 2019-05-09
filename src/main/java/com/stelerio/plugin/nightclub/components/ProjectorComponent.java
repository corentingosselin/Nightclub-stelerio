package com.stelerio.plugin.nightclub.components;

import com.artemis.Component;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

public class ProjectorComponent extends Component{

    public LaserType laserType = LaserType.GUARDIAN;

    public enum LaserType {
        GUARDIAN,
        DRAGON
    }

}
