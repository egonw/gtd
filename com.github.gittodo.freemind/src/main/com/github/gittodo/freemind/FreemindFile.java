/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 *
 * License: LGPL v3
 */
package com.github.gittodo.freemind;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Serializer;

import org.openscience.gittodo.model.IProject;
import org.openscience.gittodo.model.Item.PRIORITY;

public class FreemindFile {

    private FreemindProject root;
    private Map<String,FreemindProject> parentProjects;
    private Map<String,FreemindProject> projectNames;
    
    public FreemindFile(File file) throws Exception {
        parentProjects = new HashMap<String,FreemindProject>();
        projectNames = new HashMap<String,FreemindProject>();
        if (file.exists()) {
            FileInputStream stream = new FileInputStream(file);

            Builder parser = new Builder();
            Document doc = parser.build(stream);
            root = processTree(doc.getRootElement());
            stream.close();
        } else {
            root = new FreemindProject();
            root.setName("  All  ");
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
            parentProjects.put(childProject.getName(), parentProject);
            projectNames.put(childProject.getName(), childProject);
            processChildProjects(child, childProject);
        }
    }

    private PRIORITY createChildProjects(Element parent, FreemindProject parentProject) {
        PRIORITY maxPriority = parentProject.getMaxPriority();
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
//                STUFF to add info on what's open for a project
                if (child.getOpenCount() > 0) {
                      Element attachment = new Element("icon");
                      attachment.addAttribute(new Attribute("BUILTIN","attach"));
                      childElem.appendChild(attachment);
                      // Append the number of items per priority
//                    StringBuffer result = new StringBuffer();
//                    for (Item.PRIORITY priority : Item.PRIORITY.values()) {
//                        for (Item item : child.items(priority).values()) {
//                            if (item.getState() == Item.STATE.OPEN) {
//                                result.append(priority.ordinal());
//                            }
//                        }
//                    }
//                    Element workPackage = new Element("node");
//                    workPackage.addAttribute(new Attribute("TEXT",result.toString()));
//                    childElem.appendChild(workPackage);
                }
                parent.appendChild(childElem);
                PRIORITY childPriority = createChildProjects(childElem, child);
                if (maxPriority.ordinal() >  childPriority.ordinal()) maxPriority = childPriority;
            }
        }
        if (!parentProject.getName().contains("All") &&
            maxPriority == PRIORITY.URGENT || maxPriority == PRIORITY.TODAY &&
            parent.getChildElements("icon").size() == 0) {
            Element urgentIconElem = new Element("icon");
            urgentIconElem.addAttribute(new Attribute("BUILTIN","messagebox_warning"));
            parent.appendChild(urgentIconElem);
        }
        return maxPriority;
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
        for (String childName : parentProjects.keySet()) {
            if (parentProjects.get(childName).getName().equals(parent.getName())) {
                children.add(projectNames.get(childName));
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
            parentProjects.put(project.getName(), root);
        }
        projectNames.put(project.getName(), (FreemindProject)project);
    }
    
    public void save(File file) throws Exception {
        FileOutputStream writer = new FileOutputStream(file);
        Element rootElement = new Element("map");
        rootElement.addAttribute(new Attribute("version", "0.7.1"));
        Element allElement = new Element("node");
        allElement.addAttribute(new Attribute("TEXT", root.getName()));
        createChildProjects(allElement, root);
        rootElement.appendChild(allElement);
        Serializer serializer = new Serializer(writer);
        serializer.setIndent(2);
        Document document = new Document(rootElement);
        serializer.write(document);
        writer.close();
    }
    
}
