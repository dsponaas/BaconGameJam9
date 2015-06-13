package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/13/15.
 */
public class ExplosionComponent extends Component {

    public float timeLeft;

    public ExplosionComponent(float timeLeftInit) {
        timeLeft = timeLeftInit;
    }

}
