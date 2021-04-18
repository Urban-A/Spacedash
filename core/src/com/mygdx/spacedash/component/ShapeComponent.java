package com.mygdx.spacedash.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ShapeComponent  implements Component, Pool.Poolable {
    public float r = 0;
    public float g = 0;
    public float b = 0;
    public ShapeRenderer.ShapeType type = ShapeRenderer.ShapeType.Line;
    public Array<Float> coords = new Array<Float>(false, 5);

    @Override
    public void reset(){
        r=g=b=0;
        type = null;
        ShapeRenderer.ShapeType type = null;
        Array<Float> coords = null;

    }
}
