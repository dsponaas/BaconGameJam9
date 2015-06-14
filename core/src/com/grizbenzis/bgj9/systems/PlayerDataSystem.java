package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.grizbenzis.bgj9.Time;
import com.grizbenzis.bgj9.components.PlayerDataComponent;
import com.grizbenzis.bgj9.components.RenderComponent;

/**
 * Created by sponaas on 6/13/15.
 */
public class PlayerDataSystem extends IteratingSystem {

    private ComponentMapper<PlayerDataComponent> _playerDataComponents = ComponentMapper.getFor(PlayerDataComponent.class);
    private ComponentMapper<RenderComponent> _renderComponents = ComponentMapper.getFor(RenderComponent.class);

    public PlayerDataSystem(int priority) {
        super(Family.all(PlayerDataComponent.class).get(), priority);
    }

    private final float INVINCIBLE_BLINK_HACK = 10f;
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerDataComponent playerDataComponent = _playerDataComponents.get(entity);
        RenderComponent renderComponent = _renderComponents.get(entity);

        if(playerDataComponent.powerupTimeExplosionUp > 0f) {
            playerDataComponent.powerupTimeExplosionUp -= (float)Time.time;
        }
        if(playerDataComponent.powerupTimePoints2x > 0f) {
            playerDataComponent.powerupTimePoints2x -= (float)Time.time;
        }
        if(playerDataComponent.powerupTimeSpeedUp > 0f) {
            playerDataComponent.powerupTimeSpeedUp -= (float)Time.time;
        }

        if(playerDataComponent.invincibilityTime > 0f) {
            boolean visible = true;
            float hackVal = playerDataComponent.invincibilityTime;
            while(hackVal > 0f) {
                hackVal -= INVINCIBLE_BLINK_HACK;
                visible = !visible;
            }

            if(visible && (null == renderComponent))
                entity.add(new RenderComponent(0));
            else if(!visible && (null != renderComponent))
                entity.remove(RenderComponent.class);

            playerDataComponent.invincibilityTime -= (float)Time.time;
        }
        else if((null == renderComponent) && (playerDataComponent.alive)) {
            entity.add(new RenderComponent(0));
        }
    }
}
