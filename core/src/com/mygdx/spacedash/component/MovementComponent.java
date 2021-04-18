package com.mygdx.spacedash.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class MovementComponent implements Component, Pool.Poolable {
    public float angle_rad = 0;
    public float velocitX = 0;
    public float velocitY = 0;
    public float velocityTemp = 0;

    @Override
    public void reset(){
        angle_rad=velocitX=velocitY=velocityTemp=0;
    }
}
