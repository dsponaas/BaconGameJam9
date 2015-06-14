package com.grizbenzis.bgj9.components;

import com.badlogic.ashley.core.Component;
import com.grizbenzis.bgj9.actors.EnemyType;

/**
 * Created by sponaas on 6/13/15.
 */
public class EnemyDataComponent extends Component {

    public int score;
    public EnemyType enemyType;

    public EnemyDataComponent(int scoreInit, EnemyType enemyTypeInit) {
        score = scoreInit;
        enemyType = enemyTypeInit;
    }

}
