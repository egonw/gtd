/* Copyright 2008-2010  Egon Willighagen <egonw@users.sf.net>
 *
 * License: LGPL v3
 */
package com.github.gittodo.rcp.views;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.openscience.gittodo.io.ItemWriter;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Item.BOX;
import org.openscience.gittodo.model.Item.STATE;


public class ItemEditShell {

    private final Shell child;
    private final Item itemData;
    private final GitToDoTree tree;
    private final boolean isEditing;
    private boolean itemIsClosed;

    public ItemEditShell(Composite parent, Item item, GitToDoTree someTree) throws Exception {
        isEditing = item != null;
        String title = (isEditing ? "New Item" : "Edit Item");
        this.tree = someTree;

        if (item == null) {
            this.itemData = new Item();
            this.itemData.setBox(BOX.INBOX);
            itemIsClosed = false;
        } else {
            this.itemData = item;
            itemIsClosed = (item.getState() == STATE.CLOSED);
        }

        boolean canEdit = !itemIsClosed;

        Label label;
        Combo combo;
        Text text;

        GridData gData = new GridData();
        gData.horizontalAlignment = GridData.FILL;
        gData.grabExcessHorizontalSpace = true;

        this.child = new Shell();
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
        if (isEditing) {
        	text.setText(itemData.getProject() == null ? "" : item.getProject());
        } else if (someTree.getFilter().getProjectFilter() != null) {
        	text.setText(someTree.getFilter().getProjectFilter());
        	itemData.setProject(someTree.getFilter().getProjectFilter());
        }
        text.setEditable(canEdit);
        text.setLayoutData(gData);
        text.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
                itemData.setProject(((Text)arg0.getSource()).getText());
            }
        });

        label = new Label(child, SWT.LEFT);
        label.setText("Deadline");
        text = new Text(child, SWT.FILL);
        text.setText(itemData.getDeadline() == null ? "" : item.getDeadline());
        text.setEditable(canEdit);
        text.setLayoutData(gData);
        text.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
            	String dateText = ((Text)arg0.getSource()).getText();
            	if (dateText == null || dateText.length() == 0) {
            		itemData.setDeadline(null);
        			((Text)arg0.getSource()).setBackground(
        				Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)
        			);
            	} else {
            		try {
            			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            			Date normalized = formatter.parse(dateText);
            			itemData.setDeadline(formatter.format(normalized));
            			((Text)arg0.getSource()).setBackground(
            					Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)
            			);
            		} catch (ParseException e) {
            			((Text)arg0.getSource()).setBackground(
            					Display.getCurrent().getSystemColor(SWT.COLOR_RED)
            			);
            			itemData.setDeadline(null);
            		}
            	}
            }
        });

        label = new Label(child, SWT.LEFT);
        label.setText("URL");
        text = new Text(child, SWT.FILL);
        text.setText(itemData.getUrl() == null ? "" : item.getUrl().toString());
        text.setEditable(canEdit);
        text.setLayoutData(gData);
        text.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent arg0 ) {
            	String urlText = ((Text)arg0.getSource()).getText();
            	if (urlText == null || urlText.length() == 0) {
            		itemData.setUrl(null);
            	} else {
            		try {
            			URL url = new URL(urlText);
            			itemData.setUrl(url);
            			((Text)arg0.getSource()).setBackground(
            					Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)
            			);
            		} catch (MalformedURLException e) {
            			((Text)arg0.getSource()).setBackground(
            					Display.getCurrent().getSystemColor(SWT.COLOR_RED)
            			);
            			itemData.setUrl(null);
            		}
            	}
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

        label = new Label(child, SWT.LEFT);
        label.setText("Box");
        if (canEdit) {
            combo = new Combo(child, SWT.DROP_DOWN);
            for (Item.BOX priority : Item.BOX.values()) {
                combo.add("" + priority);
            }
            if (itemData.getBox() != null) combo.setText("" + itemData.getBox());
            combo.setLayoutData(gData);
            combo.addModifyListener( new ModifyListener() {
                public void modifyText( ModifyEvent arg0 ) {
                    Combo source = (Combo)arg0.getSource();
                    int index = source.getSelectionIndex();
                    if (index != -1) {
                        itemData.setBox(Item.BOX.values()[index]);
                    }
                }
            });
        } else {
            text = new Text(child, SWT.SINGLE);
            text.setEditable(canEdit);
            text.setText("" + itemData.getBox());
            text.setLayoutData(gData);
        }

        Button button = new Button(child, SWT.CHECK);
        button.setText("Done");
        button.setSelection(itemIsClosed);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
             public void widgetSelected( SelectionEvent e ) {
                 super.widgetSelected(e);
                 if (((Button)e.getSource()).getSelection()) {
                	 itemIsClosed = true;
                 } else {
                     itemIsClosed = false;
                 }
             }
         });

        GridData fullData = new GridData();
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
                    if (itemData.isChanged() ||
                    	itemIsClosed != (itemData.getState() == STATE.CLOSED)) {
                    	if (itemIsClosed) {
                    		itemData.setState(STATE.CLOSED);
                    	} else {
                    		itemData.setState(STATE.OPEN);
                    	}
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
        child.pack();
    }

    public void open() {
        this.child.open();
    }

}
