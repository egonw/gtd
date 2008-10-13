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
import org.openscience.gittodo.model.Project;
import org.openscience.gittodo.model.Repository;
import org.openscience.gittodo.sort.ItemSorter;

public class ListProject {
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntax: list-project <PROJECT>");
			System.exit(0);
		}
		String projectName = args[0];
		IGTDRepository repos = new Repository();
		System.out.println("Repository: " + repos.getLocation());
		Project project = repos.projects().get(projectName);
		if (project != null) {
			List<Item> items = new ArrayList<Item>();
			items.addAll(project.items().values());
			ItemSorter.sortByPriority(items);
			for (Item item : items) {
				if (item.getState() == Item.STATE.OPEN) {
					System.out.println(OneLiner.format(item));
				}
			}
		}
	}
	
}
