/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
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
import org.openscience.gittodo.sort.ItemSorter;

import com.github.gittodo.rcp.views.GitToDoTree;
import com.github.gittodo.rcp.views.ItemEditShell;
import com.github.gittodo.rcp.views.ItemFilterShell;

public class GitToDo {

    public static void main( String[] args ) {
        final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setText("Git ToDo");
        FillLayout layout = new FillLayout();
        shell.setLayout(layout);
        
        final GitToDoTree tableViewer = new GitToDoTree(shell);
        
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
                        ItemEditShell child = new ItemEditShell(shell, null, tableViewer);
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
                    tableViewer.reload();
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
                    tableViewer.getFilter().reset();
                    tableViewer.update();
                }
            }
        );

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }
    
}
