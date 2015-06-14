package com.grizbenzis.bgj9.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.grizbenzis.bgj9.components.ParallaxBackgroundComponent;

import java.util.Comparator;

/**
 * Created by sponaas on 6/14/15.
 */
public class ParallaxBackgroundZComparator implements Comparator<Entity> {

    private ComponentMapper<ParallaxBackgroundComponent> _backgroundComponents = ComponentMapper.getFor(ParallaxBackgroundComponent.class);

    @Override
    public int compare( Entity e1, Entity e2 ) {
        return (int)Math.signum(_backgroundComponents.get(e1).renderOrder - _backgroundComponents.get(e2).renderOrder);
    }

}
