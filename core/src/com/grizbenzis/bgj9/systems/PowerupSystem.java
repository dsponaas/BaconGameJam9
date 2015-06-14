package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.grizbenzis.bgj9.Constants;
import com.grizbenzis.bgj9.EntityManager;
import com.grizbenzis.bgj9.GameBoardInfo;
import com.grizbenzis.bgj9.Time;
import com.grizbenzis.bgj9.components.PlayerDataComponent;
import com.grizbenzis.bgj9.components.PowerupComponent;

/**
 * Created by sponaas on 6/14/15.
 */
public class PowerupSystem extends IteratingSystem {

    private ComponentMapper<PowerupComponent> _powerupComponents = ComponentMapper.getFor(PowerupComponent.class);
    private ComponentMapper<PlayerDataComponent> _playerDataComponents = ComponentMapper.getFor(PlayerDataComponent.class);

    public PowerupSystem(int priority) {
        super(Family.all(PowerupComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PowerupComponent powerupComponent = _powerupComponents.get(entity);
        if(powerupComponent.pickedUp) {
            EntityManager.getInstance().destroyEntity(entity);
            PlayerDataComponent playerDataComponent = _playerDataComponents.get(GameBoardInfo.getInstance().getPlayer().getEntity());
            if(Constants.PowerupType.EXPLOSION_UP.ordinal() == powerupComponent.type) {
                playerDataComponent.powerupTimeExplosionUp = Constants.POWERUP_TIMER;
            }
            else if(Constants.PowerupType.SPEED_UP.ordinal() == powerupComponent.type) {
                playerDataComponent.powerupTimeSpeedUp = Constants.POWERUP_TIMER;
            }
            else if(Constants.PowerupType.POINTS_2X.ordinal() == powerupComponent.type) {
                playerDataComponent.powerupTimePoints2x = Constants.POWERUP_TIMER;
            }
        }

        powerupComponent.timer -= (float) Time.time;
        if(powerupComponent.timer < 0f) {
            EntityManager.getInstance().destroyEntity(entity);
        }
    }
}
