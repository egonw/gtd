/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.model;

public class ItemTool {

	public static Item.PRIORITY parseProperty(String priorityStr) {
		if ("NOW".equals(priorityStr)) return Item.PRIORITY.TODAY; // backwards compatibility
		for (Item.PRIORITY priority : Item.PRIORITY.values()) {
			if ((""+priority).equals(priorityStr)) return priority;
		}
		return null;
	}

	public static Item.STATE parseState(String stateStr) {
		for (Item.STATE state : Item.STATE.values()) {
			if ((""+state).equals(stateStr)) return state;
		}
		return null;
	}

	public static Item.CONTEXT parseContext(String contextStr) {
		for (Item.CONTEXT context : Item.CONTEXT.values()) {
			if ((""+context).equals(contextStr)) return context;
		}
		return null;
	}

}
