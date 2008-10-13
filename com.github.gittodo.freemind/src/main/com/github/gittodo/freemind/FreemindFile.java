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
    private Map<String,Project> projectNames;
    
    public FreemindFile(File file) throws Exception {
        parentProjects = new HashMap<Project,Project>();
        projectNames = new HashMap<String,Project>();
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
        if (!rootElement.getAttributeValue("version").equals("0.7.1"))
            throw new Exception("Only supported is the Freemind 0.7.1 file format.");
        
        Element rootProjectElement = rootElement.getChildElements().get(0);
        Project root = new Project();
        root.setName(rootProjectElement.getAttributeValue("TEXT"));
        processChildProjects(rootProjectElement, root);
        
        return root;
    }

    private void processChildProjects(Element parent, Project parentProject) {
        Elements children = parent.getChildElements();
        for (int i=0; i<children.size(); i++) {
            Element child = children.get(i);
            Project childProject = new Project();
            childProject.setName(child.getAttributeValue("TEXT"));
            if (child.getAttribute("POSITION") != null) {
                if (child.getAttributeValue("POSITION").equals("left")) {
                    leftSibling = childProject;
                } else if (child.getAttributeValue("POSITION").equals("right")) {
                    rightSibling = childProject;
                }
            }
            parentProjects.put(childProject, parentProject);
            projectNames.put(childProject.getName(), childProject);
            processChildProjects(child, childProject);
        }
    }

    private void createChildProjects(Element parent, Project parentProject) {
//        System.out.println("Adding project: " + parentProject.getName());
        for (Project child : getChildren(parentProject)) {
//            System.out.println("Adding child: " + child.getName());
            if (child.getName() != null) {
                Element childElem = new Element("node");
                childElem.addAttribute(new Attribute("TEXT",child.getName()));
                if (leftSibling != null && child.getName().equals(leftSibling.getName())) {
                    childElem.addAttribute(new Attribute("POSITION","left"));
                } else if (rightSibling != null && child.getName().equals(rightSibling.getName())) {
                    childElem.addAttribute(new Attribute("POSITION","right"));
                }
                parent.appendChild(childElem);
                createChildProjects(childElem, child);
            }
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
//        System.out.println("Finding parents for: " + parent.getName());
        for (Project child : parentProjects.keySet()) {
//            System.out.println((" child? : " + child.getName()));
            if (parentProjects.get(child).getName().equals(parent.getName())) {
//                System.out.println(" yes");
                children.add(child);
            }
        }
        return children;
    }
    
    public boolean contains(Project project) {
        return projectNames.containsKey(project.getName());
    }
    
    public void add(Project project) {
        System.out.println("Contains: " + project.getName());
        if (!contains(project)) {
//            System.out.println("Adding project: " + project.getName());
//            System.out.println("  with parent: " + root.getName());
            parentProjects.put(project, root);
        }
    }
    
    public void save(File file) throws Exception {
        FileWriter writer = new FileWriter(file);
        Element rootElement = new Element("map");
        rootElement.addAttribute(new Attribute("version", "0.7.1"));
        Element allElement = new Element("node");
        allElement.addAttribute(new Attribute("TEXT", root.getName()));
        createChildProjects(allElement, root);
        rootElement.appendChild(allElement);
        Document document = new Document(rootElement);
        writer.write(document.toXML());
        writer.close();
    }
    
}
