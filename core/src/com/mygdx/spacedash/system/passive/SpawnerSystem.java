package com.mygdx.spacedash.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.spacedash.assets.AssetDescriptors;
import com.mygdx.spacedash.component.AimlineComponent;
import com.mygdx.spacedash.component.BannerComponent;
import com.mygdx.spacedash.component.BoundsComponent;
import com.mygdx.spacedash.component.DimensionsComponent;
import com.mygdx.spacedash.component.FontComponent;
import com.mygdx.spacedash.component.MovementComponent;
import com.mygdx.spacedash.component.PickupComponent;
import com.mygdx.spacedash.component.PlayerComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.ScoreComponent;
import com.mygdx.spacedash.component.ShapeComponent;
import com.mygdx.spacedash.component.TextureComponent;
import com.mygdx.spacedash.component.TimerComponent;
import com.mygdx.spacedash.component.ZOrderComponent;

import java.util.Random;

public class SpawnerSystem extends EntitySystem {
    private PooledEngine engine;
    private AssetManager assetManager;
    private Viewport viewport;

    public SpawnerSystem(AssetManager assetManager, Viewport viewport){
        this.assetManager=assetManager;
        this.viewport=viewport;
    }

    @Override
    public void addedToEngine(Engine engine){
        this.engine=(PooledEngine)engine;
    }

    public void CreatePlayer(){
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x=100;
        position.y=100;

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.texture=assetManager.get(AssetDescriptors.PLAYER_IMG);

        DimensionsComponent dimensions = engine.createComponent(DimensionsComponent.class);
        dimensions.width=texture.texture.getWidth()*100/texture.texture.getHeight();
        dimensions.height=100;

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = 3;

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.velocitX=movement.velocitX=movement.velocitX=movement.velocityTemp=0;

        PlayerComponent player = engine.createComponent(PlayerComponent.class);

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimensions.width, dimensions.height);

        Entity entity=engine.createEntity();

        entity.add(position);
        entity.add(dimensions);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(player);
        entity.add(movement);
        entity.add(bounds);

        engine.addEntity(entity);
    }

    public void CreateBanner(){
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x=0;
        position.y=0;

        DimensionsComponent dimensions = engine.createComponent(DimensionsComponent.class);
        dimensions.width=1600;
        dimensions.height=900;

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.texture=assetManager.get(AssetDescriptors.BANNER_IMG);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = 0;

        BannerComponent banner = engine.createComponent(BannerComponent.class);

        Entity entity=engine.createEntity();

        entity.add(position);
        entity.add(dimensions);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(banner);

        engine.addEntity(entity);
    }

    public void CreateBackground(){
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x=0;
        position.y=0;

        DimensionsComponent dimensions = engine.createComponent(DimensionsComponent.class);
        dimensions.width=900;
        dimensions.height=900;

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.texture=assetManager.get(AssetDescriptors.BACKGROUND_IMG);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = 0;

        Entity entity=engine.createEntity();

        entity.add(position);
        entity.add(dimensions);
        entity.add(texture);
        entity.add(zOrder);

        engine.addEntity(entity);
    }

    public void CreatePointer(){
        ShapeComponent shape = engine.createComponent(ShapeComponent.class);
        shape.r= 0.5f;
        shape.g= 1-shape.r;
        shape.b=0.1f;
        shape.type= ShapeRenderer.ShapeType.Filled;
        shape.coords=new Array<Float>(false, 5);
        shape.coords.add(50.f,50.f,200.f,200.f);
        shape.coords.add(6.f);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = 2;

        AimlineComponent line = engine.createComponent(AimlineComponent.class);

        Entity entity=engine.createEntity();

        entity.add(shape);
        entity.add(zOrder);
        entity.add(line);

        engine.addEntity(entity);
    }

    public void CreateFontStatic(String text, float x, float y){
        FontComponent font = engine.createComponent(FontComponent.class);
        font.font = new BitmapFont();
        font.font.getData().setScale(3);
        font.text=text;

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = 1;

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x=x;
        position.y=y;

        BannerComponent banner = engine.createComponent(BannerComponent.class);

        Entity entity=engine.createEntity();

        entity.add(font);
        entity.add(zOrder);
        entity.add(position);
        entity.add(banner);

        engine.addEntity(entity);
    }

    public void CreateFontTimer(float x, float y){
        FontComponent font = engine.createComponent(FontComponent.class);
        font.font = new BitmapFont();
        font.font.getData().setScale(3);
        font.text="";

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = 1;

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x=x;
        position.y=y;

        BannerComponent banner = engine.createComponent(BannerComponent.class);

        TimerComponent timer = engine.createComponent(TimerComponent.class);
        timer.duration=10;

        ScoreComponent score = engine.createComponent(ScoreComponent.class);
        score.score=0;
        score.required=1;
        score.best=false;


        Entity entity=engine.createEntity();

        entity.add(font);
        entity.add(zOrder);
        entity.add(position);
        entity.add(banner);
        entity.add(timer);
        entity.add(score);

        engine.addEntity(entity);
    }

    public void CreateFontScoreTracker(float x, float y, boolean best, int highscore){
        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = 1;

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x=x;
        position.y=y;

        BannerComponent banner = engine.createComponent(BannerComponent.class);

        ScoreComponent score = engine.createComponent(ScoreComponent.class);
        score.score= best ? highscore : 0;
        score.required=0;
        score.best=best;

        FontComponent font = engine.createComponent(FontComponent.class);
        font.font = new BitmapFont();
        font.font.getData().setScale(3);
        font.text=""+score.score;


        Entity entity=engine.createEntity();

        entity.add(font);
        entity.add(zOrder);
        entity.add(position);
        entity.add(banner);
        entity.add(score);

        engine.addEntity(entity);
    }

    public void CreatePickup(){


        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.texture=assetManager.get(AssetDescriptors.PICKUP_IMG);

        DimensionsComponent dimensions = engine.createComponent(DimensionsComponent.class);
        dimensions.width=50;
        dimensions.height=50;

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = 1;

        PickupComponent pickup = engine.createComponent(PickupComponent.class);

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x= MathUtils.random(50, viewport.getWorldWidth()-50-dimensions.width);
        position.y= MathUtils.random(50, viewport.getWorldHeight()-50-dimensions.height);

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimensions.width, dimensions.height);

        Entity entity=engine.createEntity();

        entity.add(position);
        entity.add(dimensions);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(pickup);
        entity.add(bounds);

        engine.addEntity(entity);
    }
}
