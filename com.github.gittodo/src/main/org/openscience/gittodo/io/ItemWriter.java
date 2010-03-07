/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;

public class ItemWriter {

	private OutputStream writer;
	private Item item;

	public ItemWriter(Item item) throws IOException {
		if (!item.isChanged()) throw new IOException("Won't save an unchanged Item.");
		this.item = item;
		ensureFolder(item);
		File itemFile = createFile(item);
		this.writer = new FileOutputStream(itemFile);
	}

	public void close() throws IOException {
		writer.close();
	}

	public void ensureFolder(Item item) throws IOException {
		// ensure folder exists
		File folder = createFolder(item);
		if (folder.exists()) {
			if (!folder.isDirectory()) {
				throw new IOException("Item folder is not a directory: " + folder.getName());
			}
		} else {
			folder.mkdir();
		}
	}
	
	public void write() throws IOException {
		Properties itemProps = new Properties();
		itemProps.put("CreationDate", item.getCreationDate());
		itemProps.put("Text", item.getText());
		itemProps.put("State", "" + item.getState());
		itemProps.put("Priority", "" + item.getPriority());
		if (item.getUrl() != null) itemProps.put("URL", item.getUrl().toString());
		if (item.getProject() != null) itemProps.put("Project", item.getProject());
		if (item.getContext() != null) itemProps.put("Context", "" + item.getContext());
		if (item.getDeadline() != null) itemProps.put("Deadline", item.getDeadline());
		itemProps.store(writer, "gittodo v1");
	}

	public File createFolder(Item item) {
		return new File(
			new Repository().getLocation() + File.separator + 
			item.getCreationDate()
		);
	}
	
	public File createFile(Item item) {
		return new File(createFolder(item), item.hashCode() + ".gtd"); 
	}

}
