/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openscience.gittodo.model.Item;

public class ItemSorter {

	public static List<Item> sortByPriority(List<Item> unsorted) {
		Collections.sort(unsorted,
			new Comparator<Item>() {
				public int compare(Item item0, Item item1) {
					return (item0.getPriority().compareTo(item1.getPriority()));
				}			
			}
		);
		return unsorted;
	}
	
	public static List<Item> sortByID(List<Item> unsorted) {
		Collections.sort(unsorted,
			new Comparator<Item>() {
				public int compare(Item item0, Item item1) {
					return (Integer.valueOf(item0.hashCode()).compareTo(Integer.valueOf(item1.hashCode())));
				}			
			}
		);
		return unsorted;
	}
	
}
