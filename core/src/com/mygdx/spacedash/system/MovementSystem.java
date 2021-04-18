package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.spacedash.component.MovementComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.tools.Mappers;

public class MovementSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            MovementComponent.class,
            PositionComponent.class
    ).get();

    public MovementSystem(){
        super(FAMILY);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = Mappers.MOVEMENT.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);

        position.x+=movement.velocitX*deltaTime;
        position.y+=movement.velocitY*deltaTime;
    }

}
