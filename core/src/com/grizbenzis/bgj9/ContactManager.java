package com.grizbenzis.bgj9;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.grizbenzis.bgj9.components.BodyComponent;

/**
 * Created by sponaas on 6/13/15.
 */
public class ContactManager implements ContactListener {

    private Engine _engine;
    private World _world;
    private ComponentMapper<BodyComponent> _bodyComponents = ComponentMapper.getFor(BodyComponent.class);

    public ContactManager(Engine engine, World world) {
        _engine = engine;
        _world = world;
        _world.setContactListener(this);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
//        Body bodyA = fixtureA.getBody();
//        Body bodyB = fixtureB.getBody();
        short fixtureAType = fixtureA.getFilterData().categoryBits;
        short fixtureBType = fixtureB.getFilterData().categoryBits;
        Entity entityA = (Entity)fixtureA.getUserData();
        Entity entityB = (Entity)fixtureB.getUserData();

        if((Constants.BITMASK_EXPLOSION == fixtureAType) && (Constants.BITMASK_ENEMY == fixtureBType)) {
            // TODO: ENEMY STRUCK
        }
        else if((Constants.BITMASK_EXPLOSION == fixtureBType) && (Constants.BITMASK_ENEMY == fixtureAType)) {
            // TODO: ENEMY STRUCK
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
