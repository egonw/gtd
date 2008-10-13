/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openscience.gittodo.io.ItemWriter;
import org.openscience.gittodo.model.Item;

public class ImportFromSourceForge {
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Syntax: gtd-import-sf <DATE> <URL>");
		}
		String date = args[0];
		String urlStr = args[1];
		URL url = new URL(urlStr);
		
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(url.openStream())
		);
		String line = reader.readLine();
		Pattern artifactPattern = Pattern.compile(".*<input\\s*name=\"artifact_id\"\\s*value=\"(.*)\"\\s*type.*");
		Pattern summaryPattern = Pattern.compile(".*value=\"([^\"]*)\".*");
		String artifact = null;
		String summary = null;
		while (line != null) {
			// do the extraction of interesting data
			if (line.contains("name=\"summary\"")) {
				if (summary == null) {
					Matcher matcher = summaryPattern.matcher(line);
					if (matcher.matches()) summary = matcher.group(1);
				}
			} else if (artifact == null) {
				Matcher matcher = artifactPattern.matcher(line);
				if (matcher.matches()) artifact = matcher.group(1);
			}
			line = reader.readLine();
		}
		reader.close();
		String text = "[" + artifact + "] " + summary;
		System.out.println("Date: " + date);
		System.out.println("Text: " + text);
		Item item = new Item(date, text);
		item.setUrl(url);
		System.out.println("ID: " + item.hashCode());
		ItemWriter writer = new ItemWriter(item);
		writer.write();
		writer.close();
	}
	
}
