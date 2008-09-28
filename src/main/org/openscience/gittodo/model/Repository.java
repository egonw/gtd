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
import java.util.Properties;

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

}
