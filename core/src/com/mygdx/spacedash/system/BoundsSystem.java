package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.spacedash.component.BoundsComponent;
import com.mygdx.spacedash.component.DimensionsComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.tools.Mappers;

public class BoundsSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            BoundsComponent.class,
            PositionComponent.class,
            DimensionsComponent.class
    ).get();

    public BoundsSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bounds = Mappers.BOUNDS.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionsComponent dimension = Mappers.DIMENSION.get(entity);

        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);
    }
}
