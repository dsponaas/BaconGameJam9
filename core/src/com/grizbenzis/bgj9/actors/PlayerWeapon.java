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
import com.grizbenzis.bgj9.components.*;

/**
 * Created by sponaas on 6/13/15.
 */
public class PlayerWeapon {

    private StateMachine<PlayerWeapon> _state;
    private Player _player;
    private Vector2 _positionOffset;
    private float _direction;

    private float _chargeTimer;
    protected void resetChargeTimer()       { _chargeTimer = 0f; }
    protected void incrementChargeTimer()   { _chargeTimer += Time.time; }

    private float _cooldownTimer;
    protected void resetCooldownTimer()     { _cooldownTimer = Constants.SHOOTING_COOLDOWN_TIMER; }
    protected void decrementCooldownTimer() { _cooldownTimer -= Time.time; }

    public PlayerWeapon(Player player, Vector2 positionOffset, float direction) {
        _state = new DefaultStateMachine<PlayerWeapon>(this, PlayerWeaponState.READY);
        _player = player;
        _positionOffset = positionOffset;
        _direction = direction;
        _chargeTimer = 0f;
    }

    protected void update(boolean inputState) {
        _state.update();

        if(PlayerWeaponState.READY == _state.getCurrentState()) {
            if(inputState) {
                _state.changeState(PlayerWeaponState.CHARGING);
            }
        }
        else if(PlayerWeaponState.CHARGING == _state.getCurrentState()) {
            if(!inputState || (_chargeTimer > Constants.SHOOTING_OVERCHARGE_TIME)) {
                fire();
            }
        }
        else if(PlayerWeaponState.COOLDOWN == _state.getCurrentState()) {
            if(_cooldownTimer < 0f) {
                _state.changeState(PlayerWeaponState.READY);
//                Gdx.app.log( Constants.LOG_TAG, "weapon ready" );
            }
//            Gdx.app.log(Constants.LOG_TAG, "cooldown: " + _cooldownTimer);
        }
    }

    private void fire() {
        Entity bulletEntity = new Entity();
        SpriteComponent bulletSprite = new SpriteComponent(new Sprite(ResourceManager.getTexture("bullet")));
        Vector2 pos = new Vector2(_player.getPosition().x + _positionOffset.x, _player.getPosition().y + _positionOffset.y);

        PositionComponent bulletPosition = new PositionComponent(pos.x, pos.y);
        Body body = BodyFactory.getInstance().generate(bulletEntity, "bullet.json", new Vector2(pos.x, pos.y));
        BodyComponent bulletBody = new BodyComponent(bulletPosition, body);
        RenderComponent renderComponent = new RenderComponent(0);
        BulletComponent bulletComponent = new BulletComponent();

        float charge = 1f;
        if(_chargeTimer < Constants.SHOOTING_CHARGE_UP_TIME)
            charge = _chargeTimer / Constants.SHOOTING_CHARGE_UP_TIME;
        DepthChargeComponent depthChargeComponent = new DepthChargeComponent(charge);

        bulletEntity.add(bulletSprite).add(bulletPosition).add(bulletBody).add(renderComponent).add(depthChargeComponent).add(bulletComponent);
        EntityManager.getInstance().addEntity(bulletEntity);

        float mass = body.getMass();
//        Vector2 impulse = new Vector2(direction * mass, 2f);
        Vector2 impulse = new Vector2(_direction * .05f, .05f);
//        Gdx.app.log(Constants.LOG_TAG, "ximpulse:" + impulse.x + "  yimpulse:" + impulse.y);
        body.applyLinearImpulse(impulse.x, impulse.y, body.getWorldCenter().x, body.getWorldCenter().y, true);

        _state.changeState(PlayerWeaponState.COOLDOWN);
//        Gdx.app.log(Constants.LOG_TAG, "firing");
    }

}
