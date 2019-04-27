package com.stelerio.plugin.nightclub.components;

import com.artemis.Component;
import com.stelerio.plugin.nightclub.utils.Vector3f;

public class TransformComponent extends Component {
    public Vector3f position = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public Vector3f scale = new Vector3f();
}
