/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openscience.gittodo.model.Project;

public class ProjectSorter {

	public static void sortByPriority(List<Project> unsorted) {
		Collections.sort(unsorted,
			new Comparator<Project>() {
				public int compare(Project item0, Project item1) {
					if (item0.getMaxPriority().ordinal() < item1.getMaxPriority().ordinal()) return -1; 
					if (item0.getMaxPriority().ordinal() > item1.getMaxPriority().ordinal()) return 1; 
					return 0;
				}			
			}
		);
	}

}
