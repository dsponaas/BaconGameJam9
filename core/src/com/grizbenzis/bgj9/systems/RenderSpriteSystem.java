package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Transform;
import com.grizbenzis.bgj9.components.*;

/**
 * Created by sponaas on 6/12/15.
 */
public class RenderSpriteSystem extends SortedIteratingSystem {

    private SpriteBatch _spriteBatch;

    private ComponentMapper<SpriteComponent> _spriteComponents = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<BodyComponent> _bodyComponents = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<PlayerDataComponent> _playerDataComponents = ComponentMapper.getFor(PlayerDataComponent.class);

    public RenderSpriteSystem(SpriteBatch spriteBatchInit, int priority) {
        super(Family.all(RenderComponent.class, SpriteComponent.class, PositionComponent.class).get(), new RenderSystemZComparator(), priority);
        _spriteBatch = spriteBatchInit;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Sprite sprite = _spriteComponents.get(entity).sprite;

        float alpha = 1f;
        PlayerDataComponent playerDataComponent = _playerDataComponents.get(entity);
        if((null != playerDataComponent) && (playerDataComponent.invincibilityTime > 0f)) {
            alpha = 0.5f;
        }

        BodyComponent bodyComponent = _bodyComponents.get(entity);
        if (bodyComponent != null) {
            Transform transform = bodyComponent.body.getTransform();
            sprite.setRotation(transform.getRotation() * MathUtils.radDeg);
        }

        sprite.setAlpha(alpha);
        sprite.draw(_spriteBatch);
    }
}
