package com.stelerio.plugin.nightclub.components;

import com.artemis.Component;
import com.stelerio.plugin.nightclub.utils.VolatileVector3f;

public class TargetComponent extends Component {
    public VolatileVector3f position;
    public VolatileVector3f rotation;
    public volatile float positionTime;
    public volatile float rotationTime;
}
