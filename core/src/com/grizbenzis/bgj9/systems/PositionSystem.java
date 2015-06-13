package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.grizbenzis.bgj9.Constants;
import com.grizbenzis.bgj9.components.BodyComponent;
import com.grizbenzis.bgj9.components.PositionComponent;
import com.grizbenzis.bgj9.components.SpriteComponent;

/**
 * Created by sponaas on 6/12/15.
 */
public class PositionSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> _positionComponents = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SpriteComponent> _spriteComponents = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<BodyComponent> _bodyComponents = ComponentMapper.getFor(BodyComponent.class);

    public PositionSystem(int priority) {
        super(Family.all(PositionComponent.class).get(), priority);
    }

    public void processEntity( Entity entity, float deltaTime ) {
        PositionComponent positionComponent = _positionComponents.get(entity);
        SpriteComponent spriteComponent = _spriteComponents.get(entity);
        BodyComponent bodyComponent = _bodyComponents.get(entity);


        // Position priority: Body => PositionComponent => Sprites  (highest to lowest)
        if (bodyComponent != null) {
            positionComponent.x = (bodyComponent.body.getPosition().x * Constants.METERS_TO_PIXELS) - (spriteComponent.sprite.getWidth() / 2);
            positionComponent.y = (bodyComponent.body.getPosition().y * Constants.METERS_TO_PIXELS) - (spriteComponent.sprite.getHeight() / 2);
        }

        if (spriteComponent != null) {
            spriteComponent.sprite.setX(positionComponent.x);
            spriteComponent.sprite.setY(positionComponent.y);
        }
    }

}
