/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openscience.gittodo.model.Item;

public class ItemSorter {

	public static List<Item> sortByPriority(List<Item> unsorted) {
		Collections.sort(unsorted,
			new Comparator<Item>() {
				public int compare(Item item0, Item item1) {
					return (item0.getPriority().compareTo(item1.getPriority()));
				}			
			}
		);
		return unsorted;
	}
	
  public static List<Item> sortByContext(List<Item> unsorted) {
      Collections.sort(unsorted,
        new Comparator<Item>() {
          public int compare(Item item0, Item item1) {
              Item.CONTEXT context0 = item0.getContext();
              Item.CONTEXT context1 = item1.getContext();
              if (context0 == null && context1 == null) return 0;
              if (context0 == null) return 1;
              if (context1 == null) return -1;
              return (item0.getContext().compareTo(item1.getContext()));
          }     
        }
      );
      return unsorted;
    }
    
	public static List<Item> sortByID(List<Item> unsorted) {
		Collections.sort(unsorted,
			new Comparator<Item>() {
				public int compare(Item item0, Item item1) {
					return (Integer.valueOf(item0.hashCode()).compareTo(Integer.valueOf(item1.hashCode())));
				}			
			}
		);
		return unsorted;
	}
	
  public static List<Item> sortByTitle(List<Item> unsorted) {
      Collections.sort(unsorted,
        new Comparator<Item>() {
          public int compare(Item item0, Item item1) {
              String title0 = item0.getText();
              String title1 = item1.getText();
              if (title0 == null && title1 == null) return 0;
              if (title0 == null) return 1;
              if (title1 == null) return -1;
              return (title0.compareTo(title1));
          }     
        }
      );
      return unsorted;
    }
    
  public static List<Item> sortByProject(List<Item> unsorted) {
      Collections.sort(unsorted,
        new Comparator<Item>() {
          public int compare(Item item0, Item item1) {
              String project0 = item0.getProject();
              String project1 = item1.getProject();
              if (project0 == null && project1 == null) return 0;
              if (project0 == null) return 1;
              if (project1 == null) return -1;
            return (project0.compareTo(project1));
          }     
        }
      );
      return unsorted;
    }
    
    public static List<Item> sortByCreationDate(List<Item> unsorted) {
        Collections.sort(unsorted, new Comparator<Item>() {
            public int compare(Item item0, Item item1) {
                String date0 = item0.getCreationDate();
                String date1 = item1.getCreationDate();
                if (date0 == null && date1 == null) return 0;
                if (date0 == null) return 1;
                if (date1 == null) return -1;
                return (date0.compareTo(date1));
            }     
        }
        );
        return unsorted;
    }

}
