package com.grizbenzis.bgj9.actors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.grizbenzis.bgj9.*;
import com.grizbenzis.bgj9.components.*;

/**
 * Created by sponaas on 6/13/15.
 */
public class EnemySub extends Actor {

    private ComponentMapper<EnemyDataComponent> _enemyDataComponents = ComponentMapper.getFor(EnemyDataComponent.class);
    private ComponentMapper<PositionComponent> _positionComponents = ComponentMapper.getFor(PositionComponent.class);

    private final float SHOT_TIMER_HACK = 180f;

    private float _shotTimer;

    public EnemySub(Entity entity) {
        super(entity);
        _shotTimer = SHOT_TIMER_HACK;
    }

    private final float ENEMY_OFFSCREEN_DESTROY_BUFFER = 250f;
    @Override
    public void update() {
        super.update();

        PositionComponent positionComponent = _positionComponents.get(getEntity());
        if(positionComponent.y < (-1f * ENEMY_OFFSCREEN_DESTROY_BUFFER)) {
            EntityManager.getInstance().destroyEntity(getEntity());
            EntityManager.getInstance().removeActor(this);
        }
        else if(positionComponent.x < (-1f * ENEMY_OFFSCREEN_DESTROY_BUFFER)) {
            EntityManager.getInstance().destroyEntity(getEntity());
            EntityManager.getInstance().removeActor(this);
        }
        else if(positionComponent.x > (GameBoardInfo.getInstance().getWidth() + ENEMY_OFFSCREEN_DESTROY_BUFFER)) {
            EntityManager.getInstance().destroyEntity(getEntity());
            EntityManager.getInstance().removeActor(this);
        }

        if(null == _enemyDataComponents.get(getEntity()))
            return;
        _shotTimer -= (float)Time.time;
        if(_shotTimer < 0f) {
            _shotTimer = SHOT_TIMER_HACK;
            fire();
        }
    }

    public static Entity makeEnemyEntity(float xPos, float yPos, float xVel) {
        Entity entity = new Entity();

        Sprite sprite = new Sprite(ResourceManager.getTexture("enemy"));
        Vector2 position = new Vector2(xPos, yPos);

        Body body = BodyFactory.getInstance().generate(entity, "enemy.json", position);

        PositionComponent positionComponent = new PositionComponent(position.x, position.y);
        BodyComponent bodyComponent = new BodyComponent(positionComponent, body);
        SpriteComponent spriteComponent = new SpriteComponent(sprite);
        RenderComponent renderComponent = new RenderComponent(0);
        EnemyDataComponent enemyDataComponent = new EnemyDataComponent(10);

        entity.add(positionComponent).add(bodyComponent).add(spriteComponent).add(renderComponent).add(enemyDataComponent);

        float mass = body.getMass();
//        Vector2 impulse = new Vector2(direction * mass, 2f);
        Vector2 impulse = new Vector2(xVel * .2f, 0f);
//        Gdx.app.log(Constants.LOG_TAG, "ximpulse:" + impulse.x + "  yimpulse:" + impulse.y);
        body.applyLinearImpulse(impulse.x, impulse.y, body.getWorldCenter().x, body.getWorldCenter().y, true);

        return entity;
    }

    private final float SHOT_SPEED_HACK = 5f;

    private void fire() {
        Entity bulletEntity = new Entity();
        SpriteComponent bulletSprite = new SpriteComponent(new Sprite(ResourceManager.getTexture("enemybullet")));
        Vector2 pos = new Vector2(getPosition().x, getPosition().y);

        PositionComponent bulletPosition = new PositionComponent(pos.x, pos.y);
        Body body = BodyFactory.getInstance().generate(bulletEntity, "enemybullet.json", new Vector2(pos.x, pos.y));
        BodyComponent bulletBody = new BodyComponent(bulletPosition, body);
        RenderComponent renderComponent = new RenderComponent(0);
        BulletComponent bulletComponent = new BulletComponent();

        bulletEntity.add(bulletSprite).add(bulletPosition).add(bulletBody).add(renderComponent).add(bulletComponent);
        EntityManager.getInstance().addEntity(bulletEntity);

        float mass = body.getMass();
        Vector2 shotDirection = GameBoardInfo.getInstance().getPlayer().getCenterPos().sub(pos).nor().scl(SHOT_SPEED_HACK);

//        Vector2 impulse = new Vector2(direction * mass, 2f);
        Vector2 impulse = new Vector2(shotDirection.x * mass, shotDirection.y * mass);
//        Gdx.app.log(Constants.LOG_TAG, "ximpulse:" + impulse.x + "  yimpulse:" + impulse.y);

        body.setTransform(body.getPosition(), 45);
        body.applyLinearImpulse(impulse.x, impulse.y, body.getWorldCenter().x, body.getWorldCenter().y, true);
    }

}
