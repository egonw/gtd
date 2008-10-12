package com.github.gittodo.rcp.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.openscience.gittodo.model.Item;


public class ItemFilterShell {
    
    private final Shell child;
    private final GitToDoTree tree;
    
    public ItemFilterShell(Shell parent, GitToDoTree someTree) throws Exception {
        this.tree = someTree;
        String title = "Edit Filter";
        
        Label label;
        Combo combo;
        Text text;
        
        GridData gData = new GridData();
        gData.horizontalAlignment = GridData.FILL;
        gData.grabExcessHorizontalSpace = true;
        
        this.child = new Shell(parent);
        child.setText(title);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        this.child.setLayout(layout);
        
        // now add the widgets
        label = new Label(child, SWT.LEFT);
        label.setText("Search");
        text = new Text(child, SWT.FILL);
        text.setText(tree.getFilter().getSubstringFilter() == null ? "" : tree.getFilter().getSubstringFilter());
        text.setLayoutData(gData);
        text.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
                Text source = (Text)arg0.getSource();
                if (source.getText().length() == 0) {
                    tree.getFilter().setSubstringFilter(null);
                } else {
                    tree.getFilter().setSubstringFilter(source.getText());
                }
            }
        });

        label = new Label(child, SWT.LEFT);
        label.setText("Project");
        text = new Text(child, SWT.FILL);
        text.setText(tree.getFilter().getProjectFilter() == null ? "" : tree.getFilter().getProjectFilter());
        text.setLayoutData(gData);
        text.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
                Text source = (Text)arg0.getSource();
                if (source.getText().length() == 0) {
                    tree.getFilter().setProjectFilter(null);
                } else {
                    tree.getFilter().setProjectFilter(source.getText());
                }
            }
        });
        
        label = new Label(child, SWT.LEFT);
        label.setText("Context");
        combo = new Combo(child, SWT.DROP_DOWN);
        combo.add("ANY");
        combo.add("" + Item.CONTEXT.HOME);
        combo.add("" + Item.CONTEXT.WORK);
        combo.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
                Combo source = (Combo)arg0.getSource();
                int index = source.getSelectionIndex();
                switch (index) {
                    case 0:
                        tree.getFilter().setContextFilter(null); break;
                    case 1:
                        tree.getFilter().setContextFilter(Item.CONTEXT.HOME); break;
                    case 2:
                        tree.getFilter().setContextFilter(Item.CONTEXT.WORK); break;
                }
            }
        });
        if (tree.getFilter().getContextFilter() != null) combo.setText("" + tree.getFilter().getContextFilter());
        combo.setLayoutData(gData);
        
        label = new Label(child, SWT.LEFT);
        label.setText("Priority");
        combo = new Combo(child, SWT.DROP_DOWN);
        combo.add("ANY");
        for (Item.PRIORITY priority : Item.PRIORITY.values()) {
            combo.add("" + priority);
        }
        combo.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
                Combo source = (Combo)arg0.getSource();
                int index = source.getSelectionIndex();
                if (index == 0) {
                    tree.getFilter().setPriorityFilter(null);
                } else if (index != -1) {
                    tree.getFilter().setPriorityFilter(Item.PRIORITY.values()[index-1]);
                }
            }
        });
        if (tree.getFilter().getPriorityFilter() != null) combo.setText("" + tree.getFilter().getPriorityFilter());
        combo.setLayoutData(gData);
        
        Button button = new Button(child, SWT.PUSH);
        button.setText("Close");
        button.addSelectionListener(new SelectionAdapter() {
           @Override
            public void widgetSelected( SelectionEvent e ) {
                super.widgetSelected(e);
                tree.update();
                child.close();
            } 
        });
    }

    public void open() {
        this.child.open();
    }
    
}
