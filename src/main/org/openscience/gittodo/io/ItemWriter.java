/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;

public class ItemWriter {

	private PrintWriter writer;
	private Item item;

	public ItemWriter(Item item) throws IOException {
		this.item = item;
		File itemFile = createFile(item);
		this.writer = new PrintWriter(
			new FileWriter(itemFile)
	    );
	}

	public void close() throws IOException {
		writer.close();
	}
	
	public void write() {
		writer.println("[Item-v1]");
		writeItemField("CreationDate", item.getCreationDate());
		writeItemField("State", item.getState());
		
	}

	private void writeItemField(String field, Object value) {
		writer.println(field + "<<");
		writer.println("" + value);
		writer.println(">>");
	}

	public File createFile(Item item) {
		return new File(
			Repository.getLocation() + File.separator + 
			item.getCreationDate() + File.separator +
			item.hashCode() + ".gtd"
		); 
	}

}
