package com.grizbenzis.bgj9;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.grizbenzis.bgj9.components.BodyComponent;
import com.grizbenzis.bgj9.components.EnemyDataComponent;

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
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();
        short fixtureAType = fixtureA.getFilterData().categoryBits;
        short fixtureBType = fixtureB.getFilterData().categoryBits;
        Entity entityA = (Entity)fixtureA.getUserData();
        Entity entityB = (Entity)fixtureB.getUserData();

        // *************************************** EXPLOSION <=> ENEMY ***************************************
        if((Constants.BITMASK_EXPLOSION == fixtureAType) && (Constants.BITMASK_ENEMY == fixtureBType)) {
            EnemyDataComponent enemyDataComponent = (EnemyDataComponent)entityB.remove(EnemyDataComponent.class);
            if(null != enemyDataComponent)
                collideExplosionAndEnemy(fixtureB, bodyB, enemyDataComponent);
        }
        else if((Constants.BITMASK_EXPLOSION == fixtureBType) && (Constants.BITMASK_ENEMY == fixtureAType)) {
            EnemyDataComponent enemyDataComponent = (EnemyDataComponent)entityA.remove(EnemyDataComponent.class);
            if(null != enemyDataComponent)
                collideExplosionAndEnemy(fixtureA, bodyA, enemyDataComponent);
        }
    }

    // TODO: this shouldnt really be here but i'll probly end up cutting corners and leaving it anyways
    private void collideExplosionAndEnemy(Fixture fixture, Body body, EnemyDataComponent enemyData) {
        Filter filter = fixture.getFilterData();
        filter.maskBits = 0;
        fixture.setFilterData(filter);

        Vector2 desiredVelocity = body.getLinearVelocity().scl(-1f).add(0f, -3f);
        float mass = body.getMass();
        Vector2 impulse = new Vector2(desiredVelocity.x * mass, desiredVelocity.y * mass);

        body.applyLinearImpulse(impulse.x, impulse.y, body.getWorldCenter().x, body.getWorldCenter().y, true);

        // TODO: add in a depth bonus as well
        GameBoardInfo.getInstance().incrementScore(enemyData.score);
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
