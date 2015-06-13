package com.grizbenzis.bgj9.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.grizbenzis.bgj9.*;
import com.grizbenzis.bgj9.components.BodyComponent;
import com.grizbenzis.bgj9.components.PositionComponent;
import com.grizbenzis.bgj9.components.RenderComponent;
import com.grizbenzis.bgj9.components.SpriteComponent;

/**
 * Created by sponaas on 6/12/15.
 */
public class Player extends Actor {

    private float _fireLeftTimer;
    private float _fireRightTimer;

    public Player(Entity entity) {
        super(entity);
    }

    @Override
    public void update() {
        updateMovement();
        updateActions();

        _fireLeftTimer -= (float)Time.time;
        _fireRightTimer -= (float)Time.time;
    }

    private void updateMovement() {
        float movementInput = 0f;
        if(InputManager.moveRightActive)
            movementInput += 1f;
        if(InputManager.moveLeftActive)
            movementInput -= 1f;

        float acceleration = movementInput * (float)Time.time * Constants.BASE_PLAYER_ACCEL;

        float velocity = getBody().getLinearVelocity().x;

        float frictionAdjustment = 0f;
        if(Math.abs(movementInput) < 0.1f) {
            frictionAdjustment = (float)Time.time * Constants.SURFACE_FRICTION * -1f * Math.signum(velocity);
            if(Math.signum(frictionAdjustment + velocity) != Math.signum(velocity))
                frictionAdjustment = -1f * velocity;
        }

        float desiredVelocity = velocity + acceleration + frictionAdjustment;

        if(Math.abs(desiredVelocity) > Constants.BASE_PLAYER_MAXSPEED)
            desiredVelocity = Math.signum(desiredVelocity) * Constants.BASE_PLAYER_MAXSPEED;

        float deltaVelocity = desiredVelocity - velocity;
//        if(Math.signum(deltaVelocity.x) != Math.signum(movementInputX)) // TODO: cant remember why im doing this bu pretty sure its important
//            return;

        float mass = getBody().getMass();
        float impulse = deltaVelocity * mass;
        getBody().applyLinearImpulse(impulse, 0, getBody().getWorldCenter().x, getBody().getWorldCenter().y, true);
    }

    private void updateActions() {
        if(InputManager.fireLeftActive && (_fireLeftTimer < 0f))
            fire(-1f);
        if(InputManager.fireRightActive && (_fireRightTimer < 0f))
            fire(1f);
    }

    private void fire(float direction) {
        if(direction > 0f)
            _fireRightTimer = Constants.SHOOTING_COOLDOWN_TIMER;
        else
            _fireLeftTimer = Constants.SHOOTING_COOLDOWN_TIMER;

        Entity bulletEntity = new Entity();
        SpriteComponent bulletSprite = new SpriteComponent(new Sprite(new Texture("bullet.png")));
        Vector2 pos = new Vector2(getPosition().x, getPosition().y); // maybe create fixtures for gun location on body?

        PositionComponent bulletPosition = new PositionComponent(pos.x, pos.y);
        Body body = BodyFactory.getInstance().generate(bulletEntity, "bullet.json", new Vector2(pos.x, pos.y));
        BodyComponent bulletBody = new BodyComponent(bulletPosition, body);
        RenderComponent renderComponent = new RenderComponent(0);

        bulletEntity.add(bulletSprite).add(bulletPosition).add(bulletBody).add(renderComponent);
        EntityManager.getInstance().addEntity(bulletEntity);

        float mass = body.getMass();
//        Vector2 impulse = new Vector2(direction * mass, 2f);
        Vector2 impulse = new Vector2(direction * .05f, .05f);
        body.applyLinearImpulse(impulse.x, impulse.y, body.getWorldCenter().x, body.getWorldCenter().y, true);

        Gdx.app.log( Constants.LOG_TAG, "firing" );
    }

}
