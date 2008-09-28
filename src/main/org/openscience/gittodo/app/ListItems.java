/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;

public class ListItems {
	
	public static void main(String[] args) {
		IGTDRepository repos = new Repository();
		System.out.println("Repository: " + repos.getLocation());
		for (Item item : repos.items()) {
			System.out.print(item.getCreationDate());
			System.out.println(" ");
			System.out.print(item.getText());
		}
	}
	
}
