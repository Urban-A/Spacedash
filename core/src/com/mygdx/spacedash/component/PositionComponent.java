package com.mygdx.spacedash.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PositionComponent implements Component, Pool.Poolable {
    public float x = 0;
    public float y = 0;
    public float r = 0;

    @Override
    public void reset(){
        y=x=r=0;
    }
}
