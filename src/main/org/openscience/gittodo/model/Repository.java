/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.model;

public class Repository {

	private static String location;

	public static String getLocation() {
		return Repository.location;
	}

	public static void setLocation(String location) {
		Repository.location = location;
	}

}
