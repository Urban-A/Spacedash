package com.mygdx.spacedash.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.spacedash.GameSpacedash;
import com.mygdx.spacedash.Highscore;
import com.mygdx.spacedash.assets.AssetDescriptors;
import com.mygdx.spacedash.component.AimlineComponent;
import com.mygdx.spacedash.component.BannerComponent;
import com.mygdx.spacedash.component.DimensionsComponent;
import com.mygdx.spacedash.component.FontComponent;
import com.mygdx.spacedash.component.MovementComponent;
import com.mygdx.spacedash.component.PlayerComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.ScoreComponent;
import com.mygdx.spacedash.component.ShapeComponent;
import com.mygdx.spacedash.component.TextureComponent;
import com.mygdx.spacedash.component.TimerComponent;
import com.mygdx.spacedash.component.ZOrderComponent;
import com.mygdx.spacedash.system.AimSystem;
import com.mygdx.spacedash.system.BoundsKillSystem;
import com.mygdx.spacedash.system.BoundsSystem;
import com.mygdx.spacedash.system.CollisionSystem;
import com.mygdx.spacedash.system.CountdownSystem;
import com.mygdx.spacedash.system.MovementSystem;
import com.mygdx.spacedash.system.PlayerInputSystem;
import com.mygdx.spacedash.system.RenderSystem;
import com.mygdx.spacedash.system.RenderSystemBanner;
import com.mygdx.spacedash.system.ScoreSystem;
import com.mygdx.spacedash.system.passive.SpawnerSystem;
import com.mygdx.spacedash.utility.GdxUtils;

public class GameScreen extends ScreenAdapter {

    private final SpriteBatch batch;
    private final GameSpacedash game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private OrthographicCamera cameraBanner;
    private boolean debug;
    private Viewport viewport;
    private Viewport viewportBanner;
    private PooledEngine engine;
    private ShapeRenderer renderer;
    private boolean gameOver;
    public Highscore highscore;
    public FileHandle file;


    public GameScreen(GameSpacedash game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
        debug = true;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(900, 900, camera);
        cameraBanner = new OrthographicCamera();
        viewportBanner = new FitViewport(1600, 900, cameraBanner);
        renderer = new ShapeRenderer();

        engine = new PooledEngine();
        Restart();


    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        GdxUtils.clearScreen(Color.BLACK);
        if (gameOver)
            engine.update(0);
        else
            engine.update(delta);

        // if (GameManager.INSTANCE.isGameOver()) {
        //     game.setScreen(new MenuScreen(game));
        // }
    }

    public void Restart(){

        Json json = new Json();
        file = Gdx.files.local("score.json");
        if(file.exists()){
            highscore = json.fromJson(Highscore.class, file);
        }else{
            highscore = new Highscore(0);
        }

        final ImmutableArray<EntitySystem> systems = engine.getSystems();
        while(systems.size() > 0) {
            engine.removeSystem(systems.first());
        }
        engine.removeAllEntities();
        gameOver=false;
        engine = new PooledEngine();

        SpawnerSystem spawner = new SpawnerSystem(assetManager, viewport);
        engine.addSystem(spawner);

        spawner.CreatePickup();
        spawner.CreatePlayer();
        spawner.CreateBanner();
        spawner.CreateBackground();
        spawner.CreatePointer();
        spawner.CreateFontStatic("Time left", 1300, 850);
        spawner.CreateFontStatic("Score", 1300, 700);
        spawner.CreateFontStatic("Best", 1300, 550);
        spawner.CreateFontTimer(1300, 800);
        spawner.CreateFontScoreTracker(1300,650,false, 0);
        spawner.CreateFontScoreTracker(1300,500,true, highscore.score);


        engine.addSystem(new AimSystem(viewport));
        engine.addSystem(new PlayerInputSystem(this));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new BoundsKillSystem(this, viewport));
        engine.addSystem(new CountdownSystem());
        engine.addSystem(new ScoreSystem(this));
        engine.addSystem(new RenderSystemBanner(batch, renderer, viewportBanner));
        engine.addSystem(new RenderSystem(batch, renderer, viewport));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width*9/16, height, true);
        viewport.setScreenX((width-viewport.getScreenWidth())/2);
        viewportBanner.update(width, height, true);
    }

    @Override
    public void dispose() {
        engine.removeAllEntities();
    }

    public void yesGameOver(){
        gameOver=true;
    }

    public boolean isGameOver(){
        return(gameOver);
    }


}
