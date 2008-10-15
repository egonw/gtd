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
    private final GitToDoTree tree;
    private final boolean isEditing;
    
    public ItemEditShell(Shell parent, Item item, GitToDoTree someTree) throws Exception {
        isEditing = item != null;
        String title = (isEditing ? "New Item" : "Edit Item");
        this.tree = someTree;
        
        if (item == null) {
            this.itemData = new Item();
        } else {
            this.itemData = item;
        }
        
        boolean canEdit = itemData.getState() == Item.STATE.OPEN;
        
        Label label;
        Combo combo;
        Text text;
        
        GridData gData = new GridData();
        gData.horizontalAlignment = GridData.FILL;
        gData.grabExcessHorizontalSpace = true;
        
        this.child = new Shell(parent);
        child.setBounds(100, 100, 500, 200);
        child.setText(title);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        this.child.setLayout(layout);
        
        // now add the widgets
        label = new Label(child, SWT.LEFT);
        label.setText("Identifier");
        label = new Label(child, SWT.FILL);
        label.setLayoutData(gData);
        if (itemData.getIdentifier() != null) {
            label.setText(""+itemData.getIdentifier());
        }

        label = new Label(child, SWT.LEFT);
        label.setText("Title");
        text = new Text(child, SWT.FILL);
        text.setText(itemData.getText() == null ? "" : itemData.getText());
        text.setEditable(canEdit);
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
        text.setEditable(canEdit);
        text.setLayoutData(gData);
        text.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
                itemData.setProject(((Text)arg0.getSource()).getText());
            }
        });
        
        label = new Label(child, SWT.LEFT);
        label.setText("Context");
        if (canEdit) {
            combo = new Combo(child, SWT.DROP_DOWN);
            combo.add("");
            for (Item.CONTEXT context : Item.CONTEXT.values()) {
                combo.add("" + context);
            }
            if (itemData.getContext() != null) combo.setText("" + itemData.getContext());
            combo.setLayoutData(gData);
            combo.addModifyListener( new ModifyListener() {
                public void modifyText( ModifyEvent arg0 ) {
                    Combo source = (Combo)arg0.getSource();
                    int index = source.getSelectionIndex();
                    if (index == 0) {
                        itemData.setContext(null);
                    } else if (index != -1) {
                        itemData.setContext(Item.CONTEXT.values()[index-1]);
                    }
                }
            });
        } else {
            text = new Text(child, SWT.SINGLE);
            text.setEditable(canEdit);
            text.setText("" + itemData.getContext());
            text.setLayoutData(gData);
        }
        
        label = new Label(child, SWT.LEFT);
        label.setText("Priority");
        if (canEdit) {
            combo = new Combo(child, SWT.DROP_DOWN);
            for (Item.PRIORITY priority : Item.PRIORITY.values()) {
                combo.add("" + priority);
            }
            if (itemData.getPriority() != null) combo.setText("" + itemData.getPriority());
            combo.setLayoutData(gData);
            combo.addModifyListener( new ModifyListener() {
                public void modifyText( ModifyEvent arg0 ) {
                    Combo source = (Combo)arg0.getSource();
                    int index = source.getSelectionIndex();
                    if (index != -1) {
                        itemData.setPriority(Item.PRIORITY.values()[index]);
                    }
                }
            });
        } else {
            text = new Text(child, SWT.SINGLE);
            text.setEditable(canEdit);
            text.setText("" + itemData.getPriority());
            text.setLayoutData(gData);
        }
        
        Button button = new Button(child, SWT.CHECK);
        button.setText("Done");
        button.setSelection(itemData.getState() == Item.STATE.CLOSED);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
             public void widgetSelected( SelectionEvent e ) {
                 super.widgetSelected(e);
                 if (((Button)e.getSource()).getSelection()) {
                     itemData.setState(Item.STATE.CLOSED);
                 } else {
                     itemData.setState(Item.STATE.OPEN);
                 }
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
                        if (!isEditing) tree.getItems().add(itemData);
                        tree.update();
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
