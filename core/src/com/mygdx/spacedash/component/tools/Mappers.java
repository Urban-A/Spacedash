package com.mygdx.spacedash.component.tools;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.spacedash.component.BoundsComponent;
import com.mygdx.spacedash.component.DimensionsComponent;
import com.mygdx.spacedash.component.FontComponent;
import com.mygdx.spacedash.component.MovementComponent;
import com.mygdx.spacedash.component.PositionComponent;
import com.mygdx.spacedash.component.ScoreComponent;
import com.mygdx.spacedash.component.ShapeComponent;
import com.mygdx.spacedash.component.TextureComponent;
import com.mygdx.spacedash.component.TimerComponent;
import com.mygdx.spacedash.component.ZOrderComponent;

public final class Mappers {
    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<ZOrderComponent> ZORDER =
            ComponentMapper.getFor(ZOrderComponent.class);
    public static final ComponentMapper<DimensionsComponent> DIMENSION =
            ComponentMapper.getFor(DimensionsComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<ShapeComponent> SHAPE =
            ComponentMapper.getFor(ShapeComponent.class);
    public static final ComponentMapper<MovementComponent> MOVEMENT =
            ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<TimerComponent> TIMER =
            ComponentMapper.getFor(TimerComponent.class);
    public static final ComponentMapper<FontComponent> FONT =
            ComponentMapper.getFor(FontComponent.class);
    public static final ComponentMapper<ScoreComponent> SCORE =
            ComponentMapper.getFor(ScoreComponent.class);
    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);
}
