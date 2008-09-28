/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import java.util.Map;

import org.openscience.gittodo.io.ItemWriter;
import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;

public class SetDone {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("Syntax: set-done <ITEM-ID> <ITEM-ID-2> ...");
			System.exit(0);
		}
		IGTDRepository repos = new Repository();
		for (int i=0; i< args.length; i++) {
			Integer itemID = Integer.parseInt(args[i]);
			Map<Integer,Item> items = repos.items();
			Item item = items.get(itemID);
			if (item == null) {
				System.out.println("No item with ID: " + itemID);
			} else {
				System.out.println("Closed item: " + itemID);
				item.setState(Item.STATE.CLOSED);
				ItemWriter writer = new ItemWriter(item);
				writer.write();
				writer.close();
			}
		}
	}
	
}
