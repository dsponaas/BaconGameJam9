package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.grizbenzis.bgj9.BodyFactory;
import com.grizbenzis.bgj9.Constants;
import com.grizbenzis.bgj9.EntityManager;
import com.grizbenzis.bgj9.GameBoardInfo;
import com.grizbenzis.bgj9.components.*;

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
//            Gdx.app.log(Constants.LOG_TAG, "detonating");
            makeExplosion(positionComponent.x, positionComponent.y);

        }
    }

    private void makeExplosion(float x, float y) {
        Entity explosionEntity = new Entity();
        SpriteComponent bulletSprite = new SpriteComponent(new Sprite(new Texture("explosion.png")));

        PositionComponent positionComponent = new PositionComponent(x, y);
        Body body = BodyFactory.getInstance().generate(explosionEntity, "explosion.json", new Vector2(x, y));
        BodyComponent bodyComponent = new BodyComponent(positionComponent, body);
        RenderComponent renderComponent = new RenderComponent(0);
        ExplosionComponent explosionComponent = new ExplosionComponent(Constants.DEPTH_CHARGE_EXPLOSION_DURATION);

        explosionEntity.add(bulletSprite).add(positionComponent).add(bodyComponent).add(renderComponent).add(explosionComponent);
        EntityManager.getInstance().addEntity(explosionEntity);
    }
}
