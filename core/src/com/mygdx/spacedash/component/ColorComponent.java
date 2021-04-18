package com.mygdx.spacedash.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ColorComponent implements Component, Pool.Poolable {
    public float r = 0;
    public float g = 0;
    public float b = 0;

    @Override
    public void reset(){
        r=g=b=0;
    }
}