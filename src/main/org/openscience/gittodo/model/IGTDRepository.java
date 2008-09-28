/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.model;

import java.util.Map;

public interface IGTDRepository {

	public String getLocation();
	public void setLocation(String location);
	public Map<Integer,Item> items();
	public Map<String,Project> projects();

}