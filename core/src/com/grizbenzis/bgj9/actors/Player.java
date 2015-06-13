package com.grizbenzis.bgj9.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
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

    private PlayerWeapon _leftWeapon;
    private PlayerWeapon _rightWeapon;

    private float sizeX;
    private float sizeY;

    public Player(Entity entity) {
        super(entity);

        sizeX = 200f; // TODO: this is crappy
        sizeY = 120f; // TODO: this is crappy

        float halfSizeX = sizeX / 2;
        float halfSizeY = sizeY / 2;

        _leftWeapon = new PlayerWeapon(this, new Vector2(-75f + halfSizeX, 25f + halfSizeY), -1f); // TODO: need legit positionOffset
        _rightWeapon = new PlayerWeapon(this, new Vector2(75f + halfSizeX, 25f + halfSizeY), 1f); // TODO: need legit positionOffset
    }

    @Override
    public void update() {
        _leftWeapon.update(InputManager.fireLeftActive);
        _rightWeapon.update(InputManager.fireRightActive);

        updateMovement();
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

}
