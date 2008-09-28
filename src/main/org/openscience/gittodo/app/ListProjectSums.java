/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import java.util.Map;

import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Project;
import org.openscience.gittodo.model.Repository;

public class ListProjectSums {
	
	public static void main(String[] args) {
		IGTDRepository repos = new Repository();
		Map<String,Project> projects = repos.projects();
		for (Project project : projects.values()) {
			if (project.getName() != null) {
				StringBuffer result = new StringBuffer();
				result.append(project.getName());
				result.append(' ');
				for (Item.PRIORITY priority : Item.PRIORITY.values()) {
					for (Item item : project.items(priority).values()) {
						result.append(priority.name().charAt(0));
					}
				}
				System.out.println(result.toString());
			}
		}
	}
	
}
