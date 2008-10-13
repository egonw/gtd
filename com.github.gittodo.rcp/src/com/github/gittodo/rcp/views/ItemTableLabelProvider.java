package com.github.gittodo.rcp.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.openscience.gittodo.model.Item;

public class ItemTableLabelProvider extends LabelProvider implements ITableLabelProvider {

    public Image getColumnImage( Object arg0, int arg1 ) {
        return null;
    }

    public String getColumnText( Object element, int index ) {
        Item item = (Item)element;
        switch (index) {
            case 0:
                return item.getCreationDate();
            case 1:
                return item.getProject() == null ? "" : item.getProject();
            case 2:
                return item.getContext() == null ? "" : "" + item.getContext();
            case 3:
                return "" + item.getPriority();
            case 4:
                return item.getText();
            default:
                return "no clue";
        }
    }

}
