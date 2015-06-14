package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.grizbenzis.bgj9.GameBoardInfo;
import com.grizbenzis.bgj9.ResourceManager;
import com.grizbenzis.bgj9.Time;
import com.grizbenzis.bgj9.bgj9;
import com.grizbenzis.bgj9.components.BodyComponent;
import com.grizbenzis.bgj9.components.DeathTimerComponent;
import com.grizbenzis.bgj9.screens.GameOverScreen;

/**
 * Created by sponaas on 6/13/15.
 */
public class DeathTimerSystem extends IteratingSystem {

    private ComponentMapper<DeathTimerComponent> _deathTimerComponents = ComponentMapper.getFor(DeathTimerComponent.class);
    private ComponentMapper<BodyComponent> _bodyComponents = ComponentMapper.getFor(BodyComponent.class);

    public DeathTimerSystem(int priority) {
        super(Family.all(DeathTimerComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DeathTimerComponent deathTimerComponent = _deathTimerComponents.get(entity);
        deathTimerComponent.timer -= (float) Time.time;

        if(deathTimerComponent.timer < 0f) {
            if(GameBoardInfo.getInstance().getLives() == 0) {
                ResourceManager.getGameMusic().stop();
                bgj9.game.setScreen(new GameOverScreen());
            }
            else {
                GameBoardInfo.getInstance().decrementLives();
            }
        }
    }
}
