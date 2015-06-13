package com.grizbenzis.bgj9.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.grizbenzis.bgj9.Constants;
import com.grizbenzis.bgj9.InputManager;
import com.grizbenzis.bgj9.Time;

/**
 * Created by sponaas on 6/12/15.
 */
public class Player extends Actor {

    public Player(Entity entity) {
        super(entity);
    }

    @Override
    public void update() {
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
