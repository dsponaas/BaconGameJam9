package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/13/15.
 */
public class DeathTimerComponent extends Component {

    public float timer;

    public DeathTimerComponent(float timerInit) {
        timer = timerInit;
    }

}
