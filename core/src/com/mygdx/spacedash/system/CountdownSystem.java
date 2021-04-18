package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.mygdx.spacedash.component.FontComponent;
import com.mygdx.spacedash.component.TimerComponent;
import com.mygdx.spacedash.component.tools.Mappers;

public class CountdownSystem extends IteratingSystem {
    private static final Family FAMILY = Family.all(
            TimerComponent.class
    ).get();

    private static final Family FAMILY_FONT = Family.all(
            TimerComponent.class,
            FontComponent.class
    ).get();

    public CountdownSystem() {
        super(FAMILY);
    }

    protected void processEntity(Entity entity, float deltaTime) {
        TimerComponent timer = Mappers.TIMER.get(entity);
        timer.duration-=deltaTime;
        if(FAMILY_FONT.matches(entity)){
            FontComponent font = Mappers.FONT.get(entity);
            font.text=""+(int)Math.ceil(timer.duration);
        }
    }
}
