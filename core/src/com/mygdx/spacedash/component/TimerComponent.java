package com.mygdx.spacedash.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TimerComponent implements Component, Pool.Poolable {
    public float duration = 0;

    @Override
    public void reset(){
        duration=0;
    }
}
