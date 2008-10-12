package com.github.gittodo.rcp.views;

import org.openscience.gittodo.model.Item;

public class GitToDoTreeFilter {

    private Item.CONTEXT contextFilter;
    
    public Item.CONTEXT getContextFilter() {
    
        return contextFilter;
    }

    
    public void setContextFilter( Item.CONTEXT contextFilter ) {
    
        this.contextFilter = contextFilter;
    }

    
    public Item.PRIORITY getPriorityFilter() {
    
        return priorityFilter;
    }

    
    public void setPriorityFilter( Item.PRIORITY priorityFilter ) {
    
        this.priorityFilter = priorityFilter;
    }

    
    public String getSubstringFilter() {
    
        return substringFilter;
    }

    
    public void setSubstringFilter( String substringFilter ) {
    
        this.substringFilter = substringFilter;
    }

    
    public String getProjectFilter() {
    
        return projectFilter;
    }

    
    public void setProjectFilter( String projectFilter ) {
    
        this.projectFilter = projectFilter;
    }

    private Item.PRIORITY priorityFilter;
    private String substringFilter;
    private String projectFilter;
    
    public void reset() {
        contextFilter = null;
        priorityFilter = null;
        substringFilter = null;
        projectFilter = null;
    }
    
    public boolean matches(Item item) {
        if (item.getState() == Item.STATE.CLOSED) return false;
        if (contextFilter != null && item.getContext() != null &&
            item.getContext() != contextFilter) return false;
        if (priorityFilter != null && item.getPriority() != null &&
                item.getPriority() != priorityFilter) return false;
        if (substringFilter != null && substringFilter.length() > 0) {
            if (!item.getText().contains(substringFilter) &&
                !(item.getProject() != null && item.getProject().contains(substringFilter)))
                return false;
        }
        if (projectFilter != null && projectFilter.length() > 0) {
            if (item.getProject() == null ||
                !(item.getProject().contains(projectFilter)))
                return false;
        }
        return true;
    }
}
