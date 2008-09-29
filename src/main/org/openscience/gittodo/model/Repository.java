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
	private Map<Integer,Item> items;
	private Map<String,Project> projects;

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
		
		items = null;
	}
	
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<String,Project> projects() {
		if (projects == null) {
			projects = new HashMap<String,Project>();
			for (Item item : items().values()) {
				String projName = item.getProject();
				Project project = projects.get(projName);
				if (project == null) {
					project = new Project();
					project.setName(projName);
					projects.put(projName, project);
				}
				project.add(item);
			}
		}
		return projects;
	}
	
	public Map<Integer,Item> items() {
		if (items == null) {
			items = loadFromDir(new File(getLocation()));
		}
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
				Integer hashcode = Integer.parseInt(file.getName().substring(0, file.getName().indexOf(".gtd")));
				try {
					reader = new ItemReader(
						new FileReader(file),
						hashcode
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
