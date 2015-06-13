package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.grizbenzis.bgj9.components.PositionComponent;

/**
 * Created by sponaas on 6/12/15.
 */
public class PositionSystem extends IteratingSystem {

    public PositionSystem(int priority) {
        super(Family.all(PositionComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // TODO: stuff
    }

}
