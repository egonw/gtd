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
import org.openscience.gittodo.io.ItemWriter;
import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Repository;

public class SetProperty {
	
	public static void main(String[] args) throws Exception {
        // create Options object
        Options options = new Options();
        options.addOption(new Option("c", "context", true, "new context"));
        // options.addOption(new Option("p", "priority", true, "new priority"));
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
        try {
        	cmd = parser.parse(options, args);
        } catch (Exception e) {
        	HelpFormatter formatter = new HelpFormatter();
        	formatter.printHelp( "gtd-set-property", options );
        	System.exit(-1);
		}
        List<String> itemIDs = new ArrayList<String>();
        for (String arg : args) {
        	if (arg.startsWith("-")) {
            	// check option once more? This could be smarter, not?
        	} else {
        		itemIDs.add(arg);
        	}
        }

		Item.CONTEXT context = null;
		if (cmd.hasOption("c")) {
			context = cmd.getOptionValue("c").equals("HOME") ? Item.CONTEXT.HOME : Item.CONTEXT.WORK;
			System.out.println("New context: " + context);
		}

		IGTDRepository repos = new Repository();
		for (String itemIDStr : itemIDs) {
			Integer itemID = Integer.parseInt(itemIDStr);
			Item item = repos.items().get(itemID);
			if (context != null) item.setContext(context);
			
			if (item.isChanged()) {
				ItemWriter writer = new ItemWriter(item);
				writer.write();
				writer.close();
			}
		}
	}
	
}
