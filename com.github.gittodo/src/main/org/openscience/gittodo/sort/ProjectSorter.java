/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openscience.gittodo.model.IProject;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Project;

public class ProjectSorter {

	public static void sortByPriority(List<Project> unsorted) {
		Collections.sort(unsorted,
			new Comparator<Project>() {
				public int compare(Project item0, Project item1) {
					if (item0.getMaxPriority().ordinal() < item1.getMaxPriority().ordinal()) return -1; 
					if (item0.getMaxPriority().ordinal() > item1.getMaxPriority().ordinal()) return 1; 
					return compare(item0, item1, item0.getMaxPriority());
				}
				private int compare(IProject item0, IProject item1, Item.PRIORITY priority) {
					int count0 = item0.itemCount(priority);
					int count1 = item1.itemCount(priority);
					if (count1 < count0) return -1;
					if (count1 > count0) return 1;
					if (priority == Item.PRIORITY.TODAY) {
						return compare(item0, item1, Item.PRIORITY.HIGH);
					}
					if (priority == Item.PRIORITY.HIGH) {
						return compare(item0, item1, Item.PRIORITY.MEDIUM);
					}
					if (priority == Item.PRIORITY.MEDIUM) {
						return compare(item0, item1, Item.PRIORITY.UNSET);
					}
					if (priority == Item.PRIORITY.UNSET) {
						return compare(item0, item1, Item.PRIORITY.LOW);
					}
					if (priority == Item.PRIORITY.LOW) {
						return compare(item0, item1, Item.PRIORITY.DELAYED);
					}
					return 0;
				}
			}
		);
	}

}
