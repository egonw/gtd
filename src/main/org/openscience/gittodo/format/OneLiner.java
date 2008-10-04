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
		result.append(FormatHelpers.formatInteger(item.hashCode(), 12));
		result.append(" ");
		result.append(FormatHelpers.formatString(""+item.getState(), 6));
		result.append(" ");
		result.append(FormatHelpers.formatString(""+item.getPriority(), 7));
		result.append(" ");
		result.append(item.getContext() == null ? "    " : item.getContext());
		result.append(" ");
		result.append(item.getCreationDate());
		result.append(" ");
		result.append(item.getText());
		return result.toString();
	}

}
