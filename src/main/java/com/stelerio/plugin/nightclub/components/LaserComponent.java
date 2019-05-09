package com.stelerio.plugin.nightclub.components;

import com.artemis.Component;
import com.stelerio.plugin.nightclub.utils.PacketUtil;

public class LaserComponent extends Component {

    public int distance = 10;

    //-1 means the laser does not exist
    public int targetID = -1;
    public int sourceID = -1;

}
