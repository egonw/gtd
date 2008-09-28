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

public class SetProject {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Syntax: set-project <PROJECT> <ITEM-ID> <ITEM-ID-2> ...");
			System.exit(0);
		}
		String project = args[0];
		for (int i=1; i< args.length; i++) {
			Integer itemID = Integer.parseInt(args[i]);
			IGTDRepository repos = new Repository();
			Map<Integer,Item> items = repos.items();
			Item item = items.get(itemID);
			if (item == null) {
				System.out.println("No item with ID: " + itemID);
			} else {
				item.setProject(project);
				ItemWriter writer = new ItemWriter(item);
				writer.write();
				writer.close();
			}
		}
	}
	
}
