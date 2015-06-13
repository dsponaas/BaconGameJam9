package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.grizbenzis.bgj9.Constants;
import com.grizbenzis.bgj9.EntityManager;
import com.grizbenzis.bgj9.GameBoardInfo;
import com.grizbenzis.bgj9.components.DepthChargeComponent;
import com.grizbenzis.bgj9.components.PositionComponent;

/**
 * Created by sponaas on 6/13/15.
 */
public class DepthChargeSystem extends IteratingSystem {

    private ComponentMapper<DepthChargeComponent> _depthChargeComponents = ComponentMapper.getFor(DepthChargeComponent.class);
    private ComponentMapper<PositionComponent> _positionComponents = ComponentMapper.getFor(PositionComponent.class);

    public DepthChargeSystem(int priority) {
        super(Family.all(DepthChargeComponent.class, PositionComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DepthChargeComponent depthChargeComponent = _depthChargeComponents.get(entity);
        PositionComponent positionComponent = _positionComponents.get(entity);

        float minDepth = GameBoardInfo.getInstance().getWaterLevel() - Constants.MIN_DETONATION_DEPTH_IN_PIXELS;
        float maxDepth = Constants.FLOOR_DETONATION_BUFFER_IN_PIXELS;
        float deltaDepth = Math.abs(maxDepth - minDepth);

        float detonationDepth = minDepth - (depthChargeComponent.depth * deltaDepth);
        float curDepth = positionComponent.y;
        if(curDepth < detonationDepth) {
            EntityManager.getInstance().destroyEntity(entity);
            Gdx.app.log(Constants.LOG_TAG, "detonating");

            // TODO: make explosion
        }
    }
}
