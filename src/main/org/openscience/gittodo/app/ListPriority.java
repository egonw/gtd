/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import org.openscience.gittodo.format.OneLiner;
import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;

public class ListPriority {
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntax: list-priority <PRIORITY>");
			System.exit(0);
		}
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
		} else if ("LOW".equals(priorityString)) {
			priority = Item.PRIORITY.LOW;
		} else {
			System.out.println("Unknown priority: " + priorityString);
			System.exit(-1);
		}
		IGTDRepository repos = new Repository();
		for (Item item : repos.items().values()) {
			if (item.getPriority() == priority) {
				System.out.println(OneLiner.format(item));
			}
		}
	}
	
}
