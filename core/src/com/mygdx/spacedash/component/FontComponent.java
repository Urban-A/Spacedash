package com.mygdx.spacedash.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Pool;

public class FontComponent implements Component, Pool.Poolable {
    public BitmapFont font = null;
    public String text = "";

    @Override
    public void reset(){
        font=null;
        text="";
    }
}