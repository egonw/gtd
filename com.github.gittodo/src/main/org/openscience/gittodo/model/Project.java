/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.model;

import java.util.HashMap;
import java.util.Map;

public class Project implements IProject {

	private Map<Item.STATE,Map<Integer,Item>> itemsByState;
	private Map<Item.PRIORITY,Map<Integer,Item>> itemsByPriority;
	private String name;
	private Item.PRIORITY maxPriority = Item.PRIORITY.DELAYED;
	private int openCount = 0;
	private int closedCount = 0;
	
	public Project() {
		itemsByState = new HashMap<Item.STATE,Map<Integer,Item>>();
		itemsByPriority = new HashMap<Item.PRIORITY,Map<Integer,Item>>();
	}
	
	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#getName()
     */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#setName(java.lang.String)
     */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#add(org.openscience.gittodo.model.Item)
     */
	public void add(Item item) {
		if (itemsByState.get(item.getState()) == null) {
			itemsByState.put(item.getState(), new HashMap<Integer,Item>());
		}
		itemsByState.get(item.getState()).put(item.hashCode(), item);
		
		if (itemsByPriority.get(item.getPriority()) == null) {
			itemsByPriority.put(item.getPriority(), new HashMap<Integer,Item>());
		}
		itemsByPriority.get(item.getPriority()).put(item.hashCode(), item);
		
		if (item.getState() == Item.STATE.OPEN) {
			openCount++;
			if (item.getPriority().ordinal() < maxPriority.ordinal()) {
				maxPriority = item.getPriority();
			}
		} else {
			closedCount++;
		}
	}
	
	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#getMaxPriority()
     */
	public Item.PRIORITY getMaxPriority() {
		return maxPriority;
	}
	
	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#items()
     */
	public Map<Integer,Item> items() {
		Map<Integer,Item> result = new HashMap<Integer,Item>();
		for (Item.STATE state : itemsByState.keySet()) {
			result.putAll(itemsByState.get(state));
		}
		return result;
	}
	
	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#items(org.openscience.gittodo.model.Item.STATE)
     */
	public Map<Integer,Item> items(Item.STATE state) {
		Map<Integer,Item> result = new HashMap<Integer,Item>();
		if (itemsByState.containsKey(state)) {
			result.putAll(itemsByState.get(state));
		}
		return result;
	}
	
	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#items(org.openscience.gittodo.model.Item.PRIORITY)
     */
	public Map<Integer,Item> items(Item.PRIORITY priority) {
		Map<Integer,Item> result = new HashMap<Integer,Item>();
		if (itemsByPriority.containsKey(priority)) {
			result.putAll(itemsByPriority.get(priority));
		}
		return result;
	}
	
	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#getOpenCount()
     */
	public int getOpenCount() {
		return openCount;
	}

	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#getClosedCount()
     */
	public int getClosedCount() {
		return closedCount;
	}

	/* (non-Javadoc)
     * @see org.openscience.gittodo.model.IProject#itemCount(org.openscience.gittodo.model.Item.PRIORITY)
     */
	public int itemCount(Item.PRIORITY priority) {
		if (itemsByPriority.get(priority) == null) return 0;
		int count = 0;
		for (Item item : itemsByPriority.get(priority).values()) {
			if (item.getState() == Item.STATE.OPEN) count++;
		}
		return count;
	}
}
