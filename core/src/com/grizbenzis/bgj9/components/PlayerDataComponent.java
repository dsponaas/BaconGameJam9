package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/13/15.
 */
public class PlayerDataComponent extends Component {

    public boolean alive;
    public float invincibilityTime;

    public float powerupTimePoints2x;
    public float powerupTimeSpeedUp;
    public float powerupTimeExplosionUp;

    public PlayerDataComponent() {
        alive = true;
        invincibilityTime = -1f;
        powerupTimePoints2x = -1f;
        powerupTimeSpeedUp = -1f;
        powerupTimeExplosionUp = -1f;
    }

}
