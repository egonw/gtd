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

public class ListProject {
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntax: list-project <PROJECT>");
			System.exit(0);
		}
		String project = args[0];
		IGTDRepository repos = new Repository();
		System.out.println("Repository: " + repos.getLocation());
		for (Item item : repos.items().values()) {
			if (item.getState() == Item.STATE.OPEN &&
			    project.equals(item.getProject())) {
				System.out.println(OneLiner.format(item));
			}
		}
	}
	
}
