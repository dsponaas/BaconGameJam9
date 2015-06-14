package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grizbenzis.bgj9.components.AnimationComponent;
import com.grizbenzis.bgj9.components.PositionComponent;
import com.grizbenzis.bgj9.components.RenderComponent;

/**
 * Created by kyle on 6/13/15.
 */
public class RenderAnimationSystem extends SortedIteratingSystem {

    private SpriteBatch _spriteBatch;

    private ComponentMapper<AnimationComponent> _animationComponents = ComponentMapper.getFor(AnimationComponent.class);

    public RenderAnimationSystem(SpriteBatch spriteBatchInit, int priority) {
        super(Family.all(RenderComponent.class, AnimationComponent.class, PositionComponent.class).get(), new RenderSystemZComparator(), priority);
        _spriteBatch = spriteBatchInit;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        float stateTime = _animationComponents.get(entity).stateTime;
        stateTime += deltaTime;

        TextureRegion currentFrame = _animationComponents.get(entity).animation.getKeyFrame(stateTime);
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);

        _spriteBatch.draw(currentFrame, positionComponent.x, positionComponent.y);
        _animationComponents.get(entity).stateTime = stateTime;
    }
}
