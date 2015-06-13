package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by sponaas on 6/12/15.
 */
public class BodyComponent extends Component {

    public Body body;
    public BodyComponent(Body bodyInit) {
        body = bodyInit;
    }

}
