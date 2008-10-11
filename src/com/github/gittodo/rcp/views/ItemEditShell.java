package com.github.gittodo.rcp.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.openscience.gittodo.model.Item;


public class ItemEditShell {
    
    private Shell child;
    private final Item itemData;
    
    public ItemEditShell(Shell parent, Item item) throws Exception {
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
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        this.child.setLayout(layout);
        
        // now add the widgets
        label = new Label(child, SWT.LEFT);
        label.setText("Identifier");
        text = new Text(child, SWT.FILL);
        text.setLayoutData(gData);
        text.setEditable(false);

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
        label.setText("Context");
        combo = new Combo(child, SWT.DROP_DOWN);
        combo.setLayoutData(gData);
    }
    
    
    
    public void open() {
        this.child.open();
    }
    
}
