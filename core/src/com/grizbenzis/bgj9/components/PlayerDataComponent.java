package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/13/15.
 */
public class PlayerDataComponent extends Component {

    public boolean alive;
    public float invincibilityTime;

    public PlayerDataComponent() {
        alive = true;
        invincibilityTime = -1f;
    }

}
