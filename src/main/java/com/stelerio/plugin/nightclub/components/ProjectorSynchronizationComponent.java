package com.stelerio.plugin.nightclub.components;

import com.artemis.Component;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

public class ProjectorSynchronizationComponent extends Component {
    //Id of armorstand to teleport (laser)
    public int targetID;
    public WrappedDataWatcher projectorDatawatcher;
    public int projectorID;
}
