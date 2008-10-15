package com.github.gittodo.freemind;

import org.openscience.gittodo.model.Project;

public class FreemindProject extends Project {

    public FreemindProject(Project project) {
        this.setName(project.getName());
    }

    public FreemindProject() {}

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

}
