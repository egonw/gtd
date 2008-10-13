package com.github.gittodo.freemind;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import org.openscience.gittodo.model.Project;

public class FreemindFile {

    private Project root;
    private Project leftSibling;
    private Project rightSibling;
    private Map<Project,Project> parentProjects;
    
    public FreemindFile(File file) throws Exception {
        parentProjects = new HashMap<Project, Project>();
        if (file.exists()) {
            FileInputStream stream = new FileInputStream(file);

            Builder parser = new Builder();
            Document doc = parser.build(stream);
            root = processTree(doc.getRootElement());
            stream.close();
        } else {
            root = new Project();
            root.setName("All");
        }
    }

    private Project processTree(Element rootElement) throws Exception {
        if (!"map".equals(rootElement.getLocalName()) ||
            rootElement.getAttribute("version") == null)
            throw new Exception("File does not seem to be a Freemind file.");
        if (!rootElement.getAttribute("version").equals("0.7.1"))
            throw new Exception("Only supported is the Freemind 0.7.1 file format.");
        
        Element rootProjectElement = rootElement.getChildElements().get(0);
        Project root = new Project();
        root.setName(rootProjectElement.getAttributeValue("TEXT"));
        processChildProjects(rootProjectElement, root);
        
        return null;
    }

    private void processChildProjects(Element parent, Project parentProject) {
        Elements children = parent.getChildElements();
        for (int i=0; i<children.size(); i++) {
            Element child = children.get(i);
            Project childProject = new Project();
            childProject.setName(child.getAttributeValue("TEXT"));
            if (child.getAttribute("POSITION") != null) {
                if (child.getAttribute("POSITION").equals("left")) {
                    leftSibling = childProject;
                } else if (child.getAttribute("POSITION").equals("right")) {
                    rightSibling = childProject;
                }
            }
            parentProjects.put(childProject, parentProject);
        }
    }

    private void createChildProjects(Element parent, Project parentProject) {
        for (Project child : getChildren(parentProject)) {
            Element childElem = new Element("node");
            childElem.addAttribute(new Attribute("TEXT",child.getName()));
            if (child.getName().equals(leftSibling.getName())) {
                childElem.addAttribute(new Attribute("POSITION","left"));
            } else if (child.getName().equals(rightSibling.getName())) {
                childElem.addAttribute(new Attribute("POSITION","right"));
            }
            parent.appendChild(childElem);
            createChildProjects(childElem, child);
        }
    }

    public Project getRootProject() {
        return root;
    }
    
    public Project getParent(Project project) {
        return parentProjects.get(project);
    }
    
    public List<Project> getChildren(Project parent) {
        List<Project> children = new ArrayList<Project>();
        // rather naive implementation, but good enough for now
        for (Project child : parentProjects.keySet()) {
            if (parentProjects.get(child).getName().equals(parent.getName())) {
                children.add(child);
            }
        }
        return children;
    }
    
    public boolean contains(Project project) {
        return parentProjects.keySet().contains(project);
    }
    
    public void add(Project project) {
        if (!contains(project)) parentProjects.put(project, root);
    }
    
    public void save(File file) throws Exception {
        FileWriter writer = new FileWriter(file);
        Element rootElement = new Element("map");
        rootElement.addAttribute(new Attribute("version", "0.7.1"));
        createChildProjects(rootElement, root);
        Document document = new Document(rootElement);
        writer.write(document.toXML());
        writer.close();
    }
    
}
