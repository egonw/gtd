/*
 * Copyright 2008  Egon Willighagen <egonw@users.sf.net>
 * 
 * License: LGPL v3
 */
package com.github.gittodo.app;

import java.io.File;

import org.openscience.gittodo.model.IGTDRepository;
import org.openscience.gittodo.model.Project;
import org.openscience.gittodo.model.Repository;

import com.github.gittodo.freemind.FreemindFile;

public class UpdateFreemind {
    public static void main(String[] args) throws Exception {
        IGTDRepository repos = new Repository();
        String fileStr = repos.getLocation() + File.separator + "all.mm";
        FreemindFile fmFile = new FreemindFile(new File(fileStr));
        for (Project project : repos.projects().values()) {
            fmFile.add(project);
        }
        fmFile.save(new File(fileStr));
    }
      
}
