package com.mygdx.spacedash.component.tools;

import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

public class ZOrderComparator implements Comparator<Entity> {
    public static final ZOrderComparator INSTANCE = new ZOrderComparator();

    private ZOrderComparator() {}

    @Override
    public int compare(Entity entity1, Entity entity2){
        return Float.compare(
                Mappers.ZORDER.get(entity1).z,
                Mappers.ZORDER.get(entity2).z
        );
    }
}
