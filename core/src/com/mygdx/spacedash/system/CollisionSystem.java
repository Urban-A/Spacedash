package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.spacedash.component.BoundsComponent;
import com.mygdx.spacedash.component.PickupComponent;
import com.mygdx.spacedash.component.PlayerComponent;
import com.mygdx.spacedash.component.tools.Mappers;

public class CollisionSystem extends EntitySystem {
    private static final Family FAMILY_PLAYER = Family.all(PlayerComponent.class, BoundsComponent.class).get();
    private static final Family FAMILY_PICKUP = Family.all(PickupComponent.class, BoundsComponent.class).get();

    @Override
    public void update(float deltaTime) {
        Entity player = getEngine().getEntitiesFor(FAMILY_PLAYER).first();
        ImmutableArray<Entity> pickups = getEngine().getEntitiesFor(FAMILY_PICKUP);
        BoundsComponent playerBounds = Mappers.BOUNDS.get(player);
        for (Entity pickup : pickups) {

            BoundsComponent pickupBounds = Mappers.BOUNDS.get(pickup);

            if (Intersector.overlaps(pickupBounds.rectangle, playerBounds.rectangle)) {
                //soundSystem.pick();
                getEngine().removeEntity(pickup);
                getEngine().getSystem(ScoreSystem.class).addPoint();
            }
        }
    }
}
