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
import org.openscience.gittodo.model.Project;
import org.openscience.gittodo.model.Repository;
import org.openscience.gittodo.sort.ProjectSorter;

public class ListProjectSums {
	
	public static void main(String[] args) {
		IGTDRepository repos = new Repository();
		List<Project> projects = new ArrayList<Project>();
		projects.addAll(repos.projects().values());
		ProjectSorter.sortByPriority(projects);
		for (Project project : projects) {
			if (project.getName() != null && project.getOpenCount() > 0) {
				StringBuffer result = new StringBuffer();
				result.append(project.getName());
				result.append(' ');
				for (Item.PRIORITY priority : Item.PRIORITY.values()) {
					for (Item item : project.items(priority).values()) {
						result.append(priority.ordinal());
					}
				}
				System.out.println(result.toString());
			}
		}
	}
	
}
