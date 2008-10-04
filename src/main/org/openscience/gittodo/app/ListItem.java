/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import org.openscience.gittodo.format.FullDetails;
import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;

public class ListItem {
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntax: list-item <ID>");
			System.exit(0);
		}
		String idStr = args[0];
		Integer id = Integer.valueOf(idStr);
		IGTDRepository repos = new Repository();
		Item item = repos.items().get(id);
		if (item != null) {
			System.out.println(FullDetails.format(item));
		}
	}
	
}
