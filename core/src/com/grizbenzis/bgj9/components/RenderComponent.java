package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/12/15.
 */
public class RenderComponent extends Component {

    public int order;

    public RenderComponent(int orderInit) {
        order = orderInit;
    }

}
