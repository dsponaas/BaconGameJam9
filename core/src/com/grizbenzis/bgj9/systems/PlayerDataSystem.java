package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.grizbenzis.bgj9.Time;
import com.grizbenzis.bgj9.components.PlayerDataComponent;

/**
 * Created by sponaas on 6/13/15.
 */
public class PlayerDataSystem extends IteratingSystem {

    private ComponentMapper<PlayerDataComponent> _playerDataComponents = ComponentMapper.getFor(PlayerDataComponent.class);

    public PlayerDataSystem(int priority) {
        super(Family.all(PlayerDataComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerDataComponent playerDataComponent = _playerDataComponents.get(entity);

        if(playerDataComponent.invincibilityTime > 0f) {
            playerDataComponent.invincibilityTime -= (float)Time.time;
        }
    }
}
