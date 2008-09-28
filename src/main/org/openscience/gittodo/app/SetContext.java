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

public class SetContext {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Syntax: set-context <CONTEXT> <ITEM-ID> <ITEM-ID-2> ...");
			System.exit(0);
		}
		IGTDRepository repos = new Repository();
		Map<Integer,Item> items = repos.items();
		String contextString = args[0];
		Item.CONTEXT context = null;
		if ("HOME".equals(contextString)) {
			context = Item.CONTEXT.HOME;
		} else if ("WORK".equals(contextString)) {
			context = Item.CONTEXT.WORK;
		} else {
			System.out.println("Unknown context: " + contextString);
			System.exit(-1);
		}
		for (int i=1; i< args.length; i++) {
			Integer itemID = Integer.parseInt(args[i]);
			Item item = items.get(itemID);
			if (item == null) {
				System.out.println("No item with ID: " + itemID);
			} else {
				item.setContext(context);
				ItemWriter writer = new ItemWriter(item);
				writer.write();
				writer.close();
			}
		}
	}
	
}
