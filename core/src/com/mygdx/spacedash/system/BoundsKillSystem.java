package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.spacedash.component.DimensionsComponent;
import com.mygdx.spacedash.component.PlayerComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.ScoreComponent;
import com.mygdx.spacedash.component.TimerComponent;
import com.mygdx.spacedash.component.tools.Mappers;
import com.mygdx.spacedash.screen.GameScreen;

import java.util.Map;

public class BoundsKillSystem extends EntitySystem {

    private static final Family FAMILY_P= Family.all(
            PlayerComponent.class
    ).get();

    private static final Family FAMILY_C= Family.all(
            ScoreComponent.class,
            TimerComponent.class
    ).get();

    private GameScreen game;
    private Entity player;
    private Entity clock;
    private Viewport viewport;

    public BoundsKillSystem(GameScreen game, Viewport viewport){
        this.game=game;
        this.viewport=viewport;
    }

    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(FAMILY_P).get(0);
        clock = engine.getEntitiesFor(FAMILY_C).get(0);
    }

    @Override
    public void update(float deltaTime) {
        TimerComponent timer = Mappers.TIMER.get(clock);
        ScoreComponent score = Mappers.SCORE.get(clock);
        DimensionsComponent dimens = Mappers.DIMENSION.get(player);
        PositionComponent pos = Mappers.POSITION.get(player);

        if(pos.y>viewport.getWorldHeight()||pos.x>viewport.getWorldWidth()||pos.y+dimens.height<0||pos.x+dimens.width<0){
            game.yesGameOver();
        }
    }
}
