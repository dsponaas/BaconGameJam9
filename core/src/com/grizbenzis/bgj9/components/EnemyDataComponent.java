package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/13/15.
 */
public class EnemyDataComponent extends Component {

    public int score;

    public EnemyDataComponent(int scoreInit) {
        score = scoreInit;
    }

}
