package com.grizbenzis.bgj9.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.grizbenzis.bgj9.BodyFactory;
import com.grizbenzis.bgj9.ResourceManager;
import com.grizbenzis.bgj9.components.BodyComponent;
import com.grizbenzis.bgj9.components.PositionComponent;
import com.grizbenzis.bgj9.components.RenderComponent;
import com.grizbenzis.bgj9.components.SpriteComponent;

/**
 * Created by sponaas on 6/13/15.
 */
public class EnemySub extends Actor {

    public EnemySub(Entity entity) {
        super(entity);
    }

    @Override
    public void update() {
        super.update();
    }

    public static Entity makeEnemyEntity(float xPos, float yPos, float xVel) {
        Entity entity = new Entity();

        Sprite sprite = new Sprite(ResourceManager.getTexture("enemy"));
        Vector2 position = new Vector2(xPos, yPos);

        Body body = BodyFactory.getInstance().generate(entity, "enemy.json", position);

        PositionComponent positionComponent = new PositionComponent(position.x, position.y);
        BodyComponent bodyComponent = new BodyComponent(positionComponent, body);
        SpriteComponent spriteComponent = new SpriteComponent(sprite);
        RenderComponent renderComponent = new RenderComponent(0);

        entity.add(positionComponent).add(bodyComponent).add(spriteComponent).add(renderComponent);

        float mass = body.getMass();
//        Vector2 impulse = new Vector2(direction * mass, 2f);
        Vector2 impulse = new Vector2(xVel * .2f, 0f);
//        Gdx.app.log(Constants.LOG_TAG, "ximpulse:" + impulse.x + "  yimpulse:" + impulse.y);
        body.applyLinearImpulse(impulse.x, impulse.y, body.getWorldCenter().x, body.getWorldCenter().y, true);

        return entity;
    }

}
