/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.io;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.openscience.gittodo.model.Item;

public class ItemReader {

	private Reader reader;

	public ItemReader(Reader reader) throws IOException {
		this.reader = reader;
	}

	public Item read() throws Exception {
		Properties itemProps = new Properties();
		itemProps.load(reader);
		Item item =  new Item(
			itemProps.getProperty("CreationDate"),
			itemProps.getProperty("Text")
		);
		if (itemProps.containsKey("Project")) {
			item.setProject(itemProps.getProperty("Project"));
		}
		return item;
	}
	
	public void close() throws IOException {
		reader.close();
	}
	
}
