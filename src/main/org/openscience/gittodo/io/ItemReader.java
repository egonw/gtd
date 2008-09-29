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
	private Integer hashcode;

	public ItemReader(Reader reader, Integer hashcode) throws IOException {
		this.reader = reader;
		this.hashcode = hashcode;
	}

	public Item read() throws Exception {
		Properties itemProps = new Properties();
		itemProps.load(reader);
		Item.STATE state = Item.STATE.OPEN;
		if (itemProps.containsKey("State")) {
			String stateStr = itemProps.getProperty("State");
			if ("OPEN".equals(stateStr)) {
				state = Item.STATE.OPEN;
			} else if ("CLOSED".equals(stateStr)) {
				state = Item.STATE.CLOSED;
			}
		}
		Item.PRIORITY priority = Item.PRIORITY.UNSET;
		if (itemProps.containsKey("Priority")) {
			String priorityStr = itemProps.getProperty("Priority");
			if ("HIGH".equals(priorityStr)) {
				priority = Item.PRIORITY.HIGH;
			} else if ("MEDIUM".equals(priorityStr)) {
				priority = Item.PRIORITY.MEDIUM;
			} else if ("LOW".equals(priorityStr)) {
				priority = Item.PRIORITY.LOW;
			} else if ("DELAYED".equals(priorityStr)) {
				priority = Item.PRIORITY.DELAYED;
			} else if ("NOW".equals(priorityStr)) { // backwards compatibility
				priority = Item.PRIORITY.TODAY;
			} else if ("TODAY".equals(priorityStr)) {
				priority = Item.PRIORITY.TODAY;
			} else if ("UNSET".equals(priorityStr)) {
				priority = Item.PRIORITY.UNSET;
			}
		}
		Item.CONTEXT context = null;
		if (itemProps.containsKey("Context")) {
			String contextStr = itemProps.getProperty("Context");
			if ("HOME".equals(contextStr)) {
				context = Item.CONTEXT.HOME;
			} else if ("WORK".equals(contextStr)) {
				context =  Item.CONTEXT.WORK;
			}
		}
		Item item =  new Item(
			itemProps.getProperty("CreationDate"),
			itemProps.getProperty("Text"),
			state, priority, context, hashcode,
			itemProps.getProperty("Project")
		);
		return item;
	}
	
	public void close() throws IOException {
		reader.close();
	}
	
}
