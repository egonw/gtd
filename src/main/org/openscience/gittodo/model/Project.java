/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.model;

import java.util.HashMap;
import java.util.Map;

public class Project {

	private Map<Item.STATE,Map<Integer,Item>> itemsByState;
	private Map<Item.PRIORITY,Map<Integer,Item>> itemsByPriority;
	private String name;
	private Item.PRIORITY maxPriority = Item.PRIORITY.DELAYED;
	private int openCount = 0;
	private int closedCount = 0;
	
	protected Project() {
		itemsByState = new HashMap<Item.STATE,Map<Integer,Item>>();
		itemsByPriority = new HashMap<Item.PRIORITY,Map<Integer,Item>>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
	
	public Item.PRIORITY getMaxPriority() {
		return maxPriority;
	}
	
	public Map<Integer,Item> items() {
		Map<Integer,Item> result = new HashMap<Integer,Item>();
		for (Item.STATE state : itemsByState.keySet()) {
			result.putAll(itemsByState.get(state));
		}
		return result;
	}
	
	public Map<Integer,Item> items(Item.STATE state) {
		Map<Integer,Item> result = new HashMap<Integer,Item>();
		if (itemsByState.containsKey(state)) {
			result.putAll(itemsByState.get(state));
		}
		return result;
	}
	
	public Map<Integer,Item> items(Item.PRIORITY priority) {
		Map<Integer,Item> result = new HashMap<Integer,Item>();
		if (itemsByPriority.containsKey(priority)) {
			result.putAll(itemsByPriority.get(priority));
		}
		return result;
	}
	
	public int getOpenCount() {
		return openCount;
	}

	public int getClosedCount() {
		return closedCount;
	}
}
