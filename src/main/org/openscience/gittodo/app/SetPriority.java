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

public class SetPriority {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Syntax: set-priority <PRIORITY> <ITEM-ID> <ITEM-ID-2> ...");
			System.exit(0);
		}
		IGTDRepository repos = new Repository();
		Map<Integer,Item> items = repos.items();
		String priorityString = args[0];
		Item.PRIORITY priority = Item.PRIORITY.TODAY;
		if ("TODAY".equals(priorityString)) {
			priority = Item.PRIORITY.TODAY;
		} else if ("DELAYED".equals(priorityString)) {
			priority = Item.PRIORITY.DELAYED;
		} else if ("HIGH".equals(priorityString)) {
			priority = Item.PRIORITY.HIGH;
		} else if ("MEDIUM".equals(priorityString)) {
			priority = Item.PRIORITY.MEDIUM;
		} else if ("URGENT".equals(priorityString)) {
			priority = Item.PRIORITY.URGENT;
		} else if ("LOW".equals(priorityString)) {
			priority = Item.PRIORITY.LOW;
		} else {
			System.out.println("Unknown priority: " + priorityString);
			System.exit(-1);
		}
		for (int i=1; i< args.length; i++) {
			Integer itemID = Integer.parseInt(args[i]);
			Item item = items.get(itemID);
			if (item == null) {
				System.out.println("No item with ID: " + itemID);
			} else {
				item.setPriority(priority);
				ItemWriter writer = new ItemWriter(item);
				writer.write();
				writer.close();
			}
		}
	}
	
}
