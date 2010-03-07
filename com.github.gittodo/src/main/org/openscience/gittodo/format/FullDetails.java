/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.format;

import org.openscience.gittodo.model.Item;

public class FullDetails {
	
	public static String format(Item item) {
		StringBuffer result = new StringBuffer();
		appendField(result, "Identifier", ""+item.hashCode());
		appendField(result, "Created", item.getCreationDate());
		appendField(result, "Deadline", (item.getDeadline() == null ? "" : ""+item.getDeadline()));
		appendField(result, "Title", item.getText());
		appendField(result, "State", ""+item.getState());
		appendField(result, "Priority", ""+item.getPriority());
		appendField(result, "Context", (item.getContext() == null ? "" : ""+item.getContext()));
		appendField(result, "Project", (item.getProject() == null ? "" : ""+item.getProject()));
		appendField(result, "URL", (item.getUrl() == null ? "" : item.getUrl().toString()));
		return result.toString();
	}
	
	private static void appendField(StringBuffer buffer, String fieldName, String fieldValue) {
		buffer.append(FormatHelpers.formatString(fieldName,10)).append(": ");
		buffer.append(fieldValue);
		buffer.append("\n");
	}
}
