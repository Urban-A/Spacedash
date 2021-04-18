package com.mygdx.spacedash.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ScoreComponent  implements Component, Pool.Poolable {
    public int score=0;
    public int required=0;
    public boolean best=false;

    @Override
    public void reset() {
        score=required=0;
        best=false;
    }
}