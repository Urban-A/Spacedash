package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.spacedash.component.AimlineComponent;
import com.mygdx.spacedash.component.DimensionsComponent;
import com.mygdx.spacedash.component.MovementComponent;
import com.mygdx.spacedash.component.PlayerComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.ShapeComponent;
import com.mygdx.spacedash.component.TextureComponent;
import com.mygdx.spacedash.component.tools.Mappers;

import javax.swing.text.View;

public class AimSystem extends EntitySystem {

    private static final Family FAMILY_PLAYER= Family.all(
            PlayerComponent.class
    ).get();
    private static final Family FAMILY_AIMLINE= Family.all(
            AimlineComponent.class
    ).get();

    private Entity player;
    private Entity line;
    private Vector3 mousePosition;
    private Vector2 originPos;
    private Viewport viewport;
    private float distanceScale, distance;
    private float distanceMax, distanceMin;
    private float minAdd, maxAdd;


    public AimSystem( Viewport viewport){
        this.viewport=viewport;
    }

    @Override
    public void addedToEngine (Engine engine) {
        player = engine.getEntitiesFor(FAMILY_PLAYER).get(0);
        line = engine.getEntitiesFor(FAMILY_AIMLINE).get(0);
        mousePosition = new Vector3(0, 0, 0);
        originPos = new Vector2(0, 0);
        distanceMax = 500;
        distanceMin= 40;
    }

    @Override
    public void update(float deltaTime) {
        DimensionsComponent playerDimens = Mappers.DIMENSION.get(player);
        PositionComponent playerPos = Mappers.POSITION.get(player);
        MovementComponent movement = Mappers.MOVEMENT.get(player);
        ShapeComponent lineShape = Mappers.SHAPE.get(line);

        originPos.set(playerPos.x+(playerDimens.width/2), playerPos.y+(playerDimens.height/2));
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(mousePosition);

        distance = (float)Math.sqrt(Math.pow(originPos.x-mousePosition.x,2)+Math.pow(originPos.y-mousePosition.y,2));
        distanceScale= distance<distanceMax ? distance : distanceMax;
        distanceScale= distanceScale>distanceMin ? distanceScale : distanceMin;
        distanceScale/=distanceMax;

        lineShape.r=distanceScale;
        lineShape.g=1-lineShape.r;

        lineShape.coords.set(0, originPos.x);
        lineShape.coords.set(1, originPos.y);
        lineShape.coords.set(2, mousePosition.x);
        lineShape.coords.set(3, mousePosition.y);

        movement.velocityTemp=distanceScale*500;
        movement.angle_rad= (float)Math.atan2(mousePosition.y-originPos.y, mousePosition.x-originPos.x);
        playerPos.r= MathUtils.radiansToDegrees * movement.angle_rad;



    }
}
