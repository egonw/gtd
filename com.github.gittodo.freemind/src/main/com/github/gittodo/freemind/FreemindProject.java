package com.github.gittodo.freemind;

import java.util.Map;

import org.openscience.gittodo.model.IProject;
import org.openscience.gittodo.model.Item;
import org.openscience.gittodo.model.Project;
import org.openscience.gittodo.model.Item.PRIORITY;
import org.openscience.gittodo.model.Item.STATE;

public class FreemindProject implements IProject {

    private IProject project = null;
    
    public FreemindProject(IProject project) {
        this.project = project;
    }

    public FreemindProject() {
        this.project = new Project(); 
    }

    private boolean leftSibling = false;

    public boolean isLeftSibling() {
        return leftSibling;
    }

    public void setLeftSibling(boolean leftSibling) {
        this.leftSibling = leftSibling;
    }

    public boolean isRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(boolean rightSibling) {
        this.rightSibling = rightSibling;
    }

    public void setFolded(boolean isFolded) {

        this.isFolded = isFolded;
    }

    public boolean isFolded() {

        return isFolded;
    }

    private boolean rightSibling = false;

    private boolean isFolded = false;

    public void add(Item item) {
        this.project.add(item);
    }

    public int getClosedCount() {
        return this.project.getClosedCount();
    }

    public PRIORITY getMaxPriority() {
        return this.project.getMaxPriority();
    }

    public String getName() {
        return this.project.getName();
    }

    public int getOpenCount() {
        return this.project.getOpenCount();
    }

    public int itemCount(PRIORITY priority) {
        return this.project.itemCount(priority);
    }

    public Map<Integer,Item> items() {
        return this.project.items();
    }

    public Map<Integer,Item> items(STATE state) {
        return this.project.items(state);
    }

    public Map<Integer,Item> items(PRIORITY priority) {
        return this.project.items(priority);
    }

    public void setName( String name ) {
        this.project.setName(name);
    }

}
