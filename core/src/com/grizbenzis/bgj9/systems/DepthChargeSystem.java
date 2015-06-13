package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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

        // TODO: get min/max depths. find depth% from positionComponent (use a buffer variable, too), if depth>tolerance grab charge (how? we're probly need a DepthCharge actor, stuff in entity userdata) then detonate
    }
}
