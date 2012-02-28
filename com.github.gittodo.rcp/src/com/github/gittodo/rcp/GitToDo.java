/* Copyright 2008-2010  Egon Willighagen <egonw@users.sf.net>
 *
 * License: LGPL v3
 */
package com.github.gittodo.rcp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.openscience.gittodo.model.Item.BOX;
import org.openscience.gittodo.model.Item.PRIORITY;
import org.openscience.gittodo.sort.ItemSorter;

import com.github.gittodo.rcp.views.GitToDoTree;
import com.github.gittodo.rcp.views.ItemEditShell;
import com.github.gittodo.rcp.views.ItemFilterShell;

public class GitToDo {

	private final Display display;
	private final Shell shell;

	// the various BOXes
	private final GitToDoTree todayTableViewer;
	private final GitToDoTree deadlineTableViewer;
	private final GitToDoTree urgentTableViewer;
	private final GitToDoTree tableViewer;
	private final GitToDoTree inboxTableViewer;
	private final GitToDoTree waitTableViewer;
	private final GitToDoTree maybeTableViewer;

	public GitToDo() {
        display = new Display();
        shell = new Shell(display);
        shell.setText("Git ToDo");
        FillLayout layout = new FillLayout();
        shell.setLayout(layout);

        TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
        TabItem todayItem = new TabItem(tabFolder, SWT.NONE);
        todayItem.setText("Today");
        todayTableViewer = new GitToDoTree(tabFolder, BOX.ACTIVE, PRIORITY.TODAY);
        todayItem.setControl(todayTableViewer.getTable()); // Possible setControl call?

        TabItem deadlineItem = new TabItem(tabFolder, SWT.NONE);
        deadlineItem.setText("Deadlines");
        deadlineTableViewer = new GitToDoTree(tabFolder, BOX.ACTIVE, true);
        deadlineItem.setControl(deadlineTableViewer.getTable()); // Possible setControl call?

        TabItem urgentItem = new TabItem(tabFolder, SWT.NONE);
        urgentItem.setText("Urgent");
        urgentTableViewer = new GitToDoTree(tabFolder, BOX.ACTIVE, PRIORITY.URGENT);
        urgentItem.setControl(urgentTableViewer.getTable()); // Possible setControl call?

        TabItem item = new TabItem(tabFolder, SWT.NONE);
        item.setText("Active");
        tableViewer = new GitToDoTree(tabFolder, BOX.ACTIVE);
        item.setControl(tableViewer.getTable()); // Possible setControl call?

        TabItem inboxItem = new TabItem(tabFolder, SWT.NONE);
        inboxItem.setText("Inbox");
        inboxTableViewer = new GitToDoTree(tabFolder, BOX.INBOX);
        inboxItem.setControl(inboxTableViewer.getTable()); // Possible setControl call?

        TabItem waitItem = new TabItem(tabFolder, SWT.NONE);
        waitItem.setText("Waiting");
        waitTableViewer = new GitToDoTree(tabFolder, BOX.WAITING);
        waitItem.setControl(waitTableViewer.getTable()); // Possible setControl call?

        TabItem maybeItem = new TabItem(tabFolder, SWT.NONE);
        maybeItem.setText("Maybe");
        maybeTableViewer = new GitToDoTree(tabFolder, BOX.MAYBE);
        maybeItem.setControl(maybeTableViewer.getTable()); // Possible setControl call?

        Menu menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar( menuBar );
        MenuItem fileMenuItem = new MenuItem(menuBar, SWT.CASCADE);
        fileMenuItem.setText( "&File" );
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenuItem.setMenu(fileMenu);
        MenuItem newItemMenu = new MenuItem(fileMenu, SWT.DROP_DOWN);
        newItemMenu.setText("&New Item\tCtlr+N");
        newItemMenu.setAccelerator(SWT.CTRL + 'N');
        newItemMenu.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    try {
                        ItemEditShell child = new ItemEditShell(
                        	shell, null, tableViewer
                        );
                        child.open();
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            }
        );
        MenuItem reloadItemMenu = new MenuItem(fileMenu, SWT.DROP_DOWN);
        reloadItemMenu.setText("&Reload\tCtlr+R");
        reloadItemMenu.setAccelerator(SWT.CTRL + 'R');
        reloadItemMenu.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    urgentTableViewer.reload();
                    todayTableViewer.reload();
                    deadlineTableViewer.reload();
                    tableViewer.reload();
                    inboxTableViewer.reload();
                    waitTableViewer.reload();
                    maybeTableViewer.reload();
                }
            }
        );
        MenuItem exitItemMenu = new MenuItem(fileMenu, SWT.DROP_DOWN);
        exitItemMenu.setText("&Exit\tCtlr+Q");
        exitItemMenu.setAccelerator(SWT.CTRL + 'Q');
        exitItemMenu.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    shell.dispose();
                }
            }
        );
        MenuItem sortMenuItem = new MenuItem(menuBar, SWT.CASCADE);
        sortMenuItem.setText( "&Sort" );
        Menu sortMenu = new Menu(shell, SWT.DROP_DOWN);
        sortMenuItem.setMenu(sortMenu);
        MenuItem priorityItemMenu = new MenuItem(sortMenu, SWT.DROP_DOWN);
        priorityItemMenu.setText("Priority\tCtlr+T");
        priorityItemMenu.setAccelerator(SWT.CTRL + 'T');
        priorityItemMenu.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    tableViewer.setActiveItems(ItemSorter.sortByPriority(tableViewer.getItems()));
                }
            }
        );
        MenuItem contextItemMenu = new MenuItem(sortMenu, SWT.DROP_DOWN);
        contextItemMenu.setText("Context\tCtlr+C");
        contextItemMenu.setAccelerator(SWT.CTRL + 'C');
        contextItemMenu.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    tableViewer.setActiveItems(ItemSorter.sortByContext(tableViewer.getItems()));
                }
            }
        );
        MenuItem projectItemMenu = new MenuItem(sortMenu, SWT.DROP_DOWN);
        projectItemMenu.setText("Project\tCtlr+P");
        projectItemMenu.setAccelerator(SWT.CTRL + 'P');
        projectItemMenu.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    tableViewer.setActiveItems(ItemSorter.sortByProject(tableViewer.getItems()));
                }
            }
        );
        MenuItem filterMenuItem = new MenuItem(menuBar, SWT.CASCADE);
        filterMenuItem.setText( "Fi&lter" );
        Menu filterMenu = new Menu(shell, SWT.DROP_DOWN);
        filterMenuItem.setMenu(filterMenu);
        MenuItem advancedItemMenu = new MenuItem(filterMenu, SWT.DROP_DOWN);
        advancedItemMenu.setText("&Advanced\tCtlr+F");
        advancedItemMenu.setAccelerator(SWT.CTRL + 'F');
        advancedItemMenu.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    try {
                        ItemFilterShell child = new ItemFilterShell(shell, tableViewer);
                        child.open();
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            }
        );
        MenuItem resetItemMenu = new MenuItem(filterMenu, SWT.DROP_DOWN);
        resetItemMenu.setText("&Reset\tCtlr+X");
        resetItemMenu.setAccelerator(SWT.CTRL + 'X');
        resetItemMenu.addSelectionListener(
            new SelectionAdapter() {
                public void widgetSelected(SelectionEvent event) {
                    resetBoxFilters();
                }
            }
        );

        shell.open();
	}
	
	public void resetBoxFilters() {
        todayTableViewer.getFilter().reset();
        deadlineTableViewer.getFilter().reset();
        urgentTableViewer.getFilter().reset();
        tableViewer.getFilter().reset();
        inboxTableViewer.getFilter().reset();
        waitTableViewer.getFilter().reset();
        maybeTableViewer.getFilter().reset();
        updateBoxes();
	}

	public void updateBoxes() {
        todayTableViewer.update();
        deadlineTableViewer.update();
        urgentTableViewer.update();
        tableViewer.update();
        inboxTableViewer.update();
        waitTableViewer.update();
        maybeTableViewer.update();
	}
	
	public boolean isDisposed() {
		return shell.isDisposed();
	}
	
	public void dispose() {
		display.dispose();
	}

    public static void main( String[] args ) {
    	GitToDo gtd = new GitToDo();
        while (!gtd.isDisposed()) {
            if (!gtd.readAndDispatch()) gtd.sleep();
        }
        gtd.dispose();
    }

	private void sleep() {
		display.sleep();
	}

	private boolean readAndDispatch() {
		return display.readAndDispatch();
	}

}
