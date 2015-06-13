package com.grizbenzis.bgj9.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.grizbenzis.bgj9.components.BodyComponent;
import com.grizbenzis.bgj9.components.PositionComponent;

/**
 * Created by sponaas on 6/12/15.
 */
public class Actor {

    private Entity _entity;
    protected Entity getEntity()        { return _entity; }

    private Body _body;
    protected Body getBody()            { return _body; }


    private PositionComponent _positionComponent;
    protected PositionComponent getPosition()     { return _positionComponent; }

    public Actor(Entity entity) {
        _entity = entity;
        _body = _entity.getComponent(BodyComponent.class).body;
        _positionComponent = _entity.getComponent(PositionComponent.class);
    }

    public void update() {}

}
