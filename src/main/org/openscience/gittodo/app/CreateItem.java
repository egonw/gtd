/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import org.openscience.gittodo.io.ItemWriter;
import org.openscience.gittodo.model.Item;

public class CreateItem {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Syntax: create-item <DATE> <TEXT>");
		}
		String date = args[0];
		StringBuffer text = new StringBuffer();
		for (int i=1; i<args.length; i++) {
			text.append(args[i]);
			if ((i+1)<args.length) text.append(' ');
		}
		System.out.println("Date: " + date);
		System.out.println("Text: " + text);
		Item item = new Item(date, text.toString()); 
		ItemWriter writer = new ItemWriter(item);
		writer.write();
		writer.close();
	}
	
}
