package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.spacedash.component.BannerComponent;
import com.mygdx.spacedash.component.DimensionsComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.ShapeComponent;
import com.mygdx.spacedash.component.TextureComponent;
import com.mygdx.spacedash.component.ZOrderComponent;
import com.mygdx.spacedash.component.tools.Mappers;
import com.mygdx.spacedash.component.tools.ZOrderComparator;

import java.util.Comparator;

public class RenderSystem extends EntitySystem implements EntityListener {

    private final SpriteBatch batch;
    private final Viewport viewport;
    private Array<Entity> sortedEntities;
    private final ImmutableArray<Entity> entities;
    private boolean shouldSort;
    private Comparator<Entity> comparator;
    private ShapeRenderer renderer;


    private static final Family FAMILY_1 = Family.all(
            PositionComponent.class,
            DimensionsComponent.class,
            TextureComponent.class,
            ZOrderComponent.class
    ).exclude(
            BannerComponent.class
    ).get();

    private static final Family FAMILY_2 = Family.all(
            ShapeComponent.class,
            ZOrderComponent.class
    ).exclude(
            BannerComponent.class
    ).get();


    public RenderSystem(SpriteBatch batch, ShapeRenderer renderer, Viewport viewport) {
        super(0 );
        this.batch = batch;
        this.viewport = viewport;
        this.renderer=renderer;
        sortedEntities = new Array<Entity>(false, 16);
        entities = new ImmutableArray<Entity>(sortedEntities);
        comparator = ZOrderComparator.INSTANCE;

    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        sort();
        batch.begin();

        for (int i = 0; i < sortedEntities.size; ++i) {
            ProcessEntity(sortedEntities.get(i), deltaTime);
        }

        batch.end();
    }

    void ProcessEntity(Entity entity, float deltaTime) {

        if(FAMILY_1.matches(entity)){
            PositionComponent position = Mappers.POSITION.get(entity);
            DimensionsComponent dimension = Mappers.DIMENSION.get(entity);
            TextureComponent texture = Mappers.TEXTURE.get(entity);

            batch.draw(texture.texture,
                    position.x, position.y,
                    dimension.width, dimension.height);
        }else if(FAMILY_2.matches(entity)){
            batch.end();

            ShapeComponent shape = Mappers.SHAPE.get(entity);
            renderer.setProjectionMatrix(viewport.getCamera().combined);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            //renderer.set(shape.type);
            renderer.setColor(shape.r, shape.g, shape.b, 1);
            renderer.rectLine(shape.coords.get(0), shape.coords.get(1), shape.coords.get(2), shape.coords.get(3), shape.coords.get(4));
            renderer.end();

            batch.begin();

        }
    }

    private void sort () {
        if (shouldSort) {
            sortedEntities.sort(comparator);
            shouldSort = false;
        }
    }

    @Override
    public void addedToEngine (Engine engine) {
        ImmutableArray<Entity> newEntities = engine.getEntitiesFor(FAMILY_1);
        ImmutableArray<Entity> newEntities2 = engine.getEntitiesFor(FAMILY_2);
        sortedEntities.clear();
        if (newEntities.size() > 0 || newEntities2.size() > 0) {
            for (int i = 0; i < newEntities.size(); ++i) {
                sortedEntities.add(newEntities.get(i));
            }
            for (int i = 0; i < newEntities2.size(); ++i) {
                sortedEntities.add(newEntities2.get(i));
            }
            sortedEntities.sort(comparator);
        }
        shouldSort = false;
        engine.addEntityListener(FAMILY_1, this);
        engine.addEntityListener(FAMILY_2, this);
    }

    @Override
    public void removedFromEngine (Engine engine) {
        engine.removeEntityListener(this);
        sortedEntities.clear();
        shouldSort = false;
    }

    @Override
    public void entityAdded (Entity entity) {
        sortedEntities.add(entity);
        shouldSort = true;
    }

    @Override
    public void entityRemoved (Entity entity) {
        sortedEntities.removeValue(entity, true);
        shouldSort = true;
    }


}
