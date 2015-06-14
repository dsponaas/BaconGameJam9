package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.grizbenzis.bgj9.BodyFactory;
import com.grizbenzis.bgj9.Constants;
import com.grizbenzis.bgj9.EntityManager;
import com.grizbenzis.bgj9.ResourceManager;
import com.grizbenzis.bgj9.components.*;

/**
 * Created by sponaas on 6/13/15.
 */
public class BulletSystem extends IteratingSystem {

    private ComponentMapper<BulletComponent> _bulletComponents = ComponentMapper.getFor(BulletComponent.class);

    public BulletSystem(int priority) {
        super(Family.all(BulletComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BulletComponent bulletComponent = _bulletComponents.get(entity);
        if(bulletComponent.detonate) {
            PositionComponent sourcePos = entity.getComponent(PositionComponent.class);

            String artFile = "explosion";
            String jsonFile = "explosion.json";
            if(bulletComponent.largeExplosion) {
                artFile = "largeexplosion";
                jsonFile = "largeexplosion.json";
            }

            Entity explosionEntity = new Entity();
            SpriteComponent bulletSprite = new SpriteComponent(new Sprite(ResourceManager.getTexture(artFile)));

            PositionComponent positionComponent = new PositionComponent(sourcePos.x, sourcePos.y);
            Body body = BodyFactory.getInstance().generate(explosionEntity, jsonFile, new Vector2(sourcePos.x, sourcePos.y));
            BodyComponent bodyComponent = new BodyComponent(positionComponent, body);
            RenderComponent renderComponent = new RenderComponent(0);
            ExplosionComponent explosionComponent = new ExplosionComponent(Constants.DEPTH_CHARGE_EXPLOSION_DURATION);

            explosionEntity.add(bulletSprite).add(positionComponent).add(bodyComponent).add(renderComponent).add(explosionComponent);
            EntityManager.getInstance().addEntity(explosionEntity);
            EntityManager.getInstance().destroyEntity(entity);

            ResourceManager.getExplosionSound().play(Constants.EXPLOSION_VOLUME);
        }
    }
}
