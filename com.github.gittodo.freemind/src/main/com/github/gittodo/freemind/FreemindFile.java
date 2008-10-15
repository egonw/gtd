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

import org.openscience.gittodo.model.IProject;

public class FreemindFile {

    private FreemindProject root;
    private Map<FreemindProject,FreemindProject> parentProjects;
    private Map<String,FreemindProject> projectNames;
    
    public FreemindFile(File file) throws Exception {
        parentProjects = new HashMap<FreemindProject,FreemindProject>();
        projectNames = new HashMap<String,FreemindProject>();
        if (file.exists()) {
            FileInputStream stream = new FileInputStream(file);

            Builder parser = new Builder();
            Document doc = parser.build(stream);
            root = processTree(doc.getRootElement());
            stream.close();
        } else {
            root = new FreemindProject();
            root.setName("All");
        }
    }

    private FreemindProject processTree(Element rootElement) throws Exception {
        if (!"map".equals(rootElement.getLocalName()) ||
            rootElement.getAttribute("version") == null)
            throw new Exception("File does not seem to be a Freemind file.");
        if (!rootElement.getAttributeValue("version").equals("0.7.1"))
            throw new Exception("Only supported is the Freemind 0.7.1 file format.");
        
        Element rootProjectElement = rootElement.getChildElements().get(0);
        FreemindProject root = new FreemindProject();
        root.setName(rootProjectElement.getAttributeValue("TEXT"));
        processChildProjects(rootProjectElement, root);
        
        return root;
    }

    private void processChildProjects(Element parent, FreemindProject parentProject) {
        Elements children = parent.getChildElements();
        for (int i=0; i<children.size(); i++) {
            Element child = children.get(i);
            FreemindProject childProject = new FreemindProject();
            childProject.setName(child.getAttributeValue("TEXT"));
            childProject.setLeftSibling("left".equals(child.getAttributeValue("POSITION")));
            childProject.setRightSibling("right".equals(child.getAttributeValue("POSITION")));
            childProject.setFolded("true".equals(child.getAttributeValue("FOLDED")));
            parentProjects.put(childProject, parentProject);
            projectNames.put(childProject.getName(), childProject);
            processChildProjects(child, childProject);
        }
    }

    private void createChildProjects(Element parent, FreemindProject parentProject) {
        for (FreemindProject child : getChildren(parentProject)) {
            if (child.getName() != null) {
                Element childElem = new Element("node");
                childElem.addAttribute(new Attribute("TEXT",child.getName()));
                if (child.isLeftSibling()) {
                    childElem.addAttribute(new Attribute("POSITION","left"));
                } else if (child.isRightSibling()) {
                    childElem.addAttribute(new Attribute("POSITION","right"));
                }
                if (child.isFolded()) {
                    childElem.addAttribute(new Attribute("FOLDED","true"));
                }
                parent.appendChild(childElem);
                createChildProjects(childElem, child);
            }
        }
    }

    public FreemindProject getRootProject() {
        return root;
    }
    
    public FreemindProject getParent(FreemindProject project) {
        return parentProjects.get(project);
    }
    
    public List<FreemindProject> getChildren(FreemindProject parent) {
        List<FreemindProject> children = new ArrayList<FreemindProject>();
        // rather naive implementation, but good enough for now
        for (FreemindProject child : parentProjects.keySet()) {
            if (parentProjects.get(child).getName().equals(parent.getName())) {
                children.add(child);
            }
        }
        return children;
    }
    
    public boolean contains(IProject project) {
        return projectNames.containsKey(project.getName());
    }
    
    public void add(IProject project) {
        if (!(project instanceof FreemindProject)) {
            project = new FreemindProject(project);
        }
        if (!contains(project)) {
            parentProjects.put((FreemindProject)project, root);
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
