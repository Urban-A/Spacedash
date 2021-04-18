package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.spacedash.Highscore;
import com.mygdx.spacedash.component.BannerComponent;
import com.mygdx.spacedash.component.FontComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.ScoreComponent;
import com.mygdx.spacedash.component.TimerComponent;
import com.mygdx.spacedash.component.ZOrderComponent;
import com.mygdx.spacedash.component.tools.Mappers;
import com.mygdx.spacedash.screen.GameScreen;
import com.mygdx.spacedash.system.passive.SpawnerSystem;

public class ScoreSystem extends EntitySystem {
    GameScreen game;
    private int highscore;


    private static final Family FAMILY= Family.all(
            ScoreComponent.class,
            TimerComponent.class
    ).get();
    private static final Family FAMILY_SCORE= Family.all(
            ScoreComponent.class,
            FontComponent.class,
            ZOrderComponent.class,
            PositionComponent.class,
            BannerComponent.class

    ).exclude(
            TimerComponent.class
    ).get();

    public ScoreSystem(GameScreen game){
        this.game=game;
        this.highscore=0;

    }

    @Override
    public void update(float deltaTime) {
        Entity clock = getEngine().getEntitiesFor(FAMILY).get(0);
        TimerComponent timer = Mappers.TIMER.get(clock);
        ScoreComponent score = Mappers.SCORE.get(clock);

        if(timer.duration<=0&&score.score<score.required){
            game.yesGameOver();
        }
    }

    public void addPoint(){
        Entity clock = getEngine().getEntitiesFor(FAMILY).get(0);
        TimerComponent timer = Mappers.TIMER.get(clock);
        ScoreComponent score = Mappers.SCORE.get(clock);
        score.score++;
        if(score.score>=score.required){

            ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY_SCORE);
            for(Entity scoreDisplay : entities){
                ScoreComponent scoreInfo = Mappers.SCORE.get(scoreDisplay);
                if(!scoreInfo.best){
                    scoreInfo.score+=(int)Math.ceil(timer.duration);
                    if(scoreInfo.score>highscore){
                        highscore=scoreInfo.score;
                    }
                }else if(scoreInfo.best){
                    if(scoreInfo.score<highscore){
                        scoreInfo.score=highscore;
                        game.highscore.score=highscore;
                        Json json = new Json();
                        json.setTypeName(null);
                        json.setUsePrototypes(false);
                        json.setIgnoreUnknownFields(true);
                        json.setOutputType(JsonWriter.OutputType.json);

                        String txt = json.toJson(game.highscore);
                        game.file.writeString(json.prettyPrint(txt), false);
                        Gdx.app.log("", ""+game.highscore.score);
                    }else{
                        highscore=scoreInfo.score;
                    }

                }
                FontComponent font = Mappers.FONT.get(scoreDisplay);
                font.text=""+scoreInfo.score;
            }

            score.score=0;
            score.required++;
            timer.duration=10;

            SpawnerSystem spawner = getEngine().getSystem(SpawnerSystem.class);
            for(int i=0; i<score.required; ++i){
                spawner.CreatePickup();
            }

        }

    }

}
