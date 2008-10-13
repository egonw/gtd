package com.github.gittodo.freemind;

import java.io.File;
import java.io.FileInputStream;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;

public class FreemindFile {

    private Element root;
    
    public FreemindFile(File file) throws Exception {
        FileInputStream stream = new FileInputStream(file);

        Builder parser = new Builder();
        Document doc = parser.build(stream);
        root = doc.getRootElement();
    }
    
}
