package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by sponaas on 6/12/15.
 */
public class SpriteComponent extends Component {

    public Sprite sprite;

    public SpriteComponent(Sprite spriteInit) {
        sprite = spriteInit;
    }

}
