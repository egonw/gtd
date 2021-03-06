/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import java.util.ArrayList;
import java.util.List;

import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;

public class ListProjects {
	
	public static void main(String[] args) {
		IGTDRepository repos = new Repository();
		List<String> projects = new ArrayList<String>();
		for (Item item : repos.items().values()) {
			String project = item.getProject();
			if (!projects.contains(project)) {
				projects.add(project);
				if (!"null".equals(""+project)) System.out.println(project);
			}
		}
	}
	
}
