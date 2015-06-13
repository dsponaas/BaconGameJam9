package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/13/15.
 */
public class DepthChargeComponent extends Component {

    public float depth;

    public DepthChargeComponent(float depthInit) {
        depth = depthInit;
    }

}
