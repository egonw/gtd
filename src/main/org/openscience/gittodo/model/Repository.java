/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openscience.gittodo.io.ItemReader;

public class Repository implements IGTDRepository {

	private String location;

	public Repository() {
		Properties reposProps = new Properties();
		try {
			reposProps.load(
				new FileReader(
					new File(
						System.getProperty("user.home") + File.separator + 
						".gtdrc"
					)
				)
			);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		location = reposProps.getProperty("Repository");
	}
	
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<Integer,Item> items() {
		Map<Integer,Item> items = loadFromDir(new File(getLocation()));
		return items;
	}

	private Map<Integer,Item> loadFromDir(File dir) {
		Map<Integer,Item> items = new HashMap<Integer,Item>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				items.putAll(loadFromDir(file));
			} else if (file.getName().endsWith(".gtd")) {
				// add file
				ItemReader reader;
				try {
					reader = new ItemReader(
						new FileReader(file)
					);
					Item item = reader.read();
					items.put(item.hashCode(), item);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return items;
	}

}
