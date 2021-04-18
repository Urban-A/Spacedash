package com.mygdx.spacedash.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class DimensionsComponent implements Component, Pool.Poolable {
    public float width = 0;
    public float height = 0;

    @Override
    public void reset(){
        width=height=50;
    }
}
