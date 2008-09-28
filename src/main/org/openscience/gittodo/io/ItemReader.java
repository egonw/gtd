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
		if (itemProps.containsKey("State")) {
			String state = itemProps.getProperty("State");
			if ("OPEN".equals(state)) {
				item.setState(Item.STATE.OPEN);
			} else if ("CLOSED".equals(state)) {
				item.setState(Item.STATE.CLOSED);
			}
		}
		if (itemProps.containsKey("Priority")) {
			String state = itemProps.getProperty("Priority");
			if ("HIGH".equals(state)) {
				item.setPriority(Item.PRIORITY.HIGH);
			} else if ("MEDIUM".equals(state)) {
				item.setPriority(Item.PRIORITY.MEDIUM);
			} else if ("LOW".equals(state)) {
				item.setPriority(Item.PRIORITY.LOW);
			} else if ("DELAYED".equals(state)) {
				item.setPriority(Item.PRIORITY.DELAYED);
			} else if ("NOW".equals(state)) {
				item.setPriority(Item.PRIORITY.NOW);
			} else if ("UNSET".equals(state)) {
				item.setPriority(Item.PRIORITY.UNSET);
			}
		}
		return item;
	}
	
	public void close() throws IOException {
		reader.close();
	}
	
}
