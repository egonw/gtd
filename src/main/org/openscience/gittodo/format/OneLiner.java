/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.format;

import org.openscience.gittodo.model.Item;

public class OneLiner {
	
	public static String format(Item item) {
		StringBuffer result = new StringBuffer();
		result.append(item.hashCode());
		result.append(" ");
		result.append(item.getState());
		result.append(" ");
		result.append(item.getPriority());
		result.append(" ");
		result.append(item.getCreationDate());
		result.append(" ");
		result.append(item.getText());
		return result.toString();
	}
	
}
