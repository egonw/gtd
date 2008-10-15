package org.openscience.gittodo.model;

import java.util.Map;

public interface IProject {

    public abstract String getName();

    public abstract void setName( String name );

    public abstract void add( Item item );

    public abstract Item.PRIORITY getMaxPriority();

    public abstract Map<Integer, Item> items();

    public abstract Map<Integer, Item> items( Item.STATE state );

    public abstract Map<Integer, Item> items( Item.PRIORITY priority );

    public abstract int getOpenCount();

    public abstract int getClosedCount();

    public abstract int itemCount( Item.PRIORITY priority );

}