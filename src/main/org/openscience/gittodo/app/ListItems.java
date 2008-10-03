/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package org.openscience.gittodo.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.openscience.gittodo.format.OneLiner;
import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;
import org.openscience.gittodo.sort.ItemSorter;

public class ListItems {
	
	public static void main(String[] args) throws Exception {
        // create Options object
        Options options = new Options();
        options.addOption(new Option("h", "home", false, "only items with the context HOME"));
        options.addOption(new Option("w", "work", false, "only items with the context WORK"));
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
        try {
        	cmd = parser.parse(options, args);
        } catch (Exception e) {
        	HelpFormatter formatter = new HelpFormatter();
        	formatter.printHelp( "gtd-list-items", options );
        	System.exit(-1);
		}

		IGTDRepository repos = new Repository();
		Item.CONTEXT context = null;
		if (cmd.hasOption("h")) {
			context = Item.CONTEXT.HOME;
		} else if (cmd.hasOption("w")) {
			context = Item.CONTEXT.WORK;
		}
		List<Item> items = new ArrayList<Item>();
		items.addAll(repos.items().values());
		ItemSorter.sortByPriority(items);
		for (Item item : items) {
			if (item.getState() == Item.STATE.OPEN &&
				(context == null ? true : item.getContext() == context)) {
				System.out.println(OneLiner.format(item));
			}
		}
	}
	
}
