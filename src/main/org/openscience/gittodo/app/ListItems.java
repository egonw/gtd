/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import java.util.ArrayList;
import java.util.List;

import org.openscience.gittodo.format.OneLiner;
import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;
import org.openscience.gittodo.sort.ItemSorter;

public class ListItems {
	
	public static void main(String[] args) {
		IGTDRepository repos = new Repository();
		List<Item> items = new ArrayList<Item>();
		items.addAll(repos.items().values());
		ItemSorter.sortByPriority(items);
		for (Item item : items) {
			if (item.getState() == Item.STATE.OPEN) {
				System.out.println(OneLiner.format(item));
			}
		}
	}
	
}
