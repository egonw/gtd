package com.github.gittodo.rcp;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.github.gittodo.rcp.views.GitToDoTree;

public class GitToDo {

    public static void main( String[] args ) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("GitToDo");
        shell.setLayout(new FillLayout());
        
        final TableViewer tableViewer = new GitToDoTree(shell);
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }

}
