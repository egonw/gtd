/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.format;

public class FormatHelpers {
	
	public static String formatInteger(int integer, int length) {
		String curInt = ("" + integer);
		while (curInt.length() < length) {
			curInt = " " + curInt;
		}
		return curInt;
	}
	
	public static String formatString(String str, int length) {
		while (str.length() < length) {
			str = " " + str;
		}
		return str;
	}
}
