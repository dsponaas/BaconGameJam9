package com.grizbenzis.bgj9.actors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
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
    private ComponentMapper<PlayerDataComponent> _playerDataComponents = ComponentMapper.getFor(PlayerDataComponent.class);
    private ComponentMapper<BodyComponent> _bodyComponents = ComponentMapper.getFor(BodyComponent.class);

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

    public static Entity makeEnemyEntity(float xPos, float yPos, float xVel, EnemyType enemyType) {
        Entity entity = new Entity();

        Vector2 position = new Vector2(xPos, yPos);

        Sprite sprite;
        Body body;

        if (enemyType == EnemyType.Type1) {
            sprite = new Sprite(ResourceManager.getTexture("enemy"));
            body = BodyFactory.getInstance().generate(entity, "enemy.json", position);
        }
        else {
            sprite = new Sprite(ResourceManager.getTexture("enemy2"));
            body = BodyFactory.getInstance().generate(entity, "enemy2.json", position);
        }

        if (xVel > 0f) {
            sprite.flip(true, false);
        }

        PositionComponent positionComponent = new PositionComponent(position.x, position.y);
        BodyComponent bodyComponent = new BodyComponent(positionComponent, body);
        SpriteComponent spriteComponent = new SpriteComponent(sprite);
        RenderComponent renderComponent = new RenderComponent(0);
        EnemyDataComponent enemyDataComponent = new EnemyDataComponent(10, enemyType);

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
        Entity playerEntity = GameBoardInfo.getInstance().getPlayer().getEntity();
        PlayerDataComponent playerDataComponent = _playerDataComponents.get(playerEntity);

        if ((playerDataComponent.alive == false) || (playerDataComponent.invincibilityTime > 0f)) {
            return;
        }

        EnemyDataComponent enemyDataComponent = _enemyDataComponents.get(getEntity());
        BodyComponent enemyBodyComponent = _bodyComponents.get(getEntity());

        // Determine direction of enemy travel
        Body enemyBody = enemyBodyComponent.body;
        Vector2 enemyVelocity = enemyBody.getLinearVelocity();

        Entity bulletEntity = new Entity();
        SpriteComponent bulletSprite = new SpriteComponent(new Sprite(ResourceManager.getTexture("enemybullet")));

        // HACK for changing bullet origin
        Vector2 pos;
        PositionComponent bulletPosition;
        if (enemyVelocity.x > 0f) {
            if (enemyDataComponent.enemyType == EnemyType.Type1){
                pos = new Vector2(getPosition().x + 200, getPosition().y);
            }
            else {
                pos = new Vector2(getPosition().x + 260, getPosition().y);
            }
        }
        else {
            pos = new Vector2(getPosition().x, getPosition().y);
        }

        bulletPosition = new PositionComponent(pos.x, pos.y);

        Body body = BodyFactory.getInstance().generate(bulletEntity, "enemybullet.json", new Vector2(pos.x, pos.y));
        BodyComponent bulletBody = new BodyComponent(bulletPosition, body);
        RenderComponent renderComponent = new RenderComponent(0);
        BulletComponent bulletComponent = new BulletComponent();

        bulletEntity.add(bulletSprite).add(bulletPosition).add(bulletBody).add(renderComponent).add(bulletComponent);
        EntityManager.getInstance().addEntity(bulletEntity);

        float mass = body.getMass();
        Vector2 shotDirection;

        if (enemyDataComponent.enemyType == EnemyType.Type1){
            shotDirection = GameBoardInfo.getInstance().getPlayer().getCenterPos().sub(pos).nor().scl(SHOT_SPEED_HACK);
        }
        else {
            shotDirection = new Vector2(0f, 1f).nor().scl(SHOT_SPEED_HACK);
        }

//        Vector2 impulse = new Vector2(direction * mass, 2f);
        Vector2 impulse = new Vector2(shotDirection.x * mass, shotDirection.y * mass);
//        Gdx.app.log(Constants.LOG_TAG, "ximpulse:" + impulse.x + "  yimpulse:" + impulse.y);

        body.setTransform(body.getPosition(), impulse.angle() * MathUtils.degRad);
        body.applyLinearImpulse(impulse.x, impulse.y, body.getWorldCenter().x, body.getWorldCenter().y, true);
    }

}
