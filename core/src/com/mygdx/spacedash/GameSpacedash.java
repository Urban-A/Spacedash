package com.mygdx.spacedash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.spacedash.assets.AssetDescriptors;
import com.mygdx.spacedash.screen.GameScreen;

public class GameSpacedash extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;

	private OrthographicCamera camera;
	private Viewport viewport;

	private OrthographicCamera camera2;
	private Viewport viewport2;
	
	@Override
	public void create () {

		assetManager = new AssetManager();
		assetManager.load(AssetDescriptors.BANNER_IMG);
		assetManager.load(AssetDescriptors.BACKGROUND_IMG);
		assetManager.load(AssetDescriptors.PLAYER_IMG);
		assetManager.load(AssetDescriptors.PICKUP_IMG);
		assetManager.finishLoading();

		batch = new SpriteBatch();

		//_________________
		camera = new OrthographicCamera();
		viewport = new FitViewport(900, 900, camera);

		camera2 = new OrthographicCamera();
		viewport2 = new FitViewport(1600, 900, camera2);
		//_________________


		setScreen(new GameScreen(this));
	}


	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
