package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kyle on 6/12/15.
 */
public class AnimationComponent extends Component {

    public Animation animation;
    public float stateTime;

    public AnimationComponent(Animation animationInit) {

        animation = animationInit;
        stateTime = 0f;
    }

}
