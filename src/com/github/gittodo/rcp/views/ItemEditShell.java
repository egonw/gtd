package com.github.gittodo.rcp.views;

import java.io.IOException;

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
import org.openscience.gittodo.io.ItemWriter;
import org.openscience.gittodo.model.Item;


public class ItemEditShell {
    
    private final Shell child;
    private final Item itemData;
    
    public ItemEditShell(Shell parent, Item item) throws Exception {
        String title = (item == null ? "New Item" : "Edit Item");
        
        if (item == null) {
            this.itemData = new Item();
        } else {
            this.itemData = item;
        }
        
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
        label.setText("Identifier");
        text = new Text(child, SWT.FILL);
        text.setLayoutData(gData);
        text.setEditable(false);
        if (itemData.getIdentifier() != null) {
            text.setText(""+itemData.getIdentifier());
        }

        label = new Label(child, SWT.LEFT);
        label.setText("Title");
        text = new Text(child, SWT.FILL);
        text.setText(itemData.getText() == null ? "" : item.getText());
        text.setLayoutData(gData);
        text.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
                itemData.setText(((Text)arg0.getSource()).getText());
            }
        });

        label = new Label(child, SWT.LEFT);
        label.setText("Project");
        text = new Text(child, SWT.FILL);
        text.setText(itemData.getProject() == null ? "" : item.getProject());
        text.setLayoutData(gData);
        text.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
                itemData.setProject(((Text)arg0.getSource()).getText());
            }
        });
        
        label = new Label(child, SWT.LEFT);
        label.setText("Context");
        combo = new Combo(child, SWT.DROP_DOWN);
        combo.add("" + Item.CONTEXT.HOME);
        combo.add("" + Item.CONTEXT.WORK);
        if (itemData.getContext() != null) combo.setText("" + itemData.getContext());
        combo.setLayoutData(gData);
        
        label = new Label(child, SWT.LEFT);
        label.setText("Priority");
        combo = new Combo(child, SWT.DROP_DOWN);
        for (Item.PRIORITY priority : Item.PRIORITY.values()) {
            combo.add("" + priority);
        }
        if (itemData.getPriority() != null) combo.setText("" + itemData.getPriority());
        combo.setLayoutData(gData);
        
        Button button = new Button(child, SWT.CHECK);
        button.setText("Done");
        button.addSelectionListener(new SelectionAdapter() {
            @Override
             public void widgetSelected( SelectionEvent e ) {
                 super.widgetSelected(e);
                 itemData.setState(Item.STATE.CLOSED);
             } 
         });GridData fullData = new GridData();
        fullData.horizontalAlignment = GridData.FILL;
        fullData.grabExcessHorizontalSpace = true;
        fullData.horizontalSpan = GridData.FILL;
        button.setLayoutData(fullData);
        
        button = new Button(child, SWT.PUSH);
        button.setText("Cancel");
        button.addSelectionListener(new SelectionAdapter() {
           @Override
            public void widgetSelected( SelectionEvent e ) {
                super.widgetSelected(e);
                child.close();
            } 
        });
        button = new Button(child, SWT.PUSH);
        button.setText("Save");
        button.addSelectionListener(new SelectionAdapter() {
           @Override
            public void widgetSelected( SelectionEvent e ) {
                super.widgetSelected(e);
                try {
                    if (itemData.isChanged()) {
                        ItemWriter writer = new ItemWriter(itemData);
                        writer.write();
                        writer.close();
                        child.close();
                    }
                } catch ( IOException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } 
        });
    }

    public void open() {
        this.child.open();
    }
    
}
