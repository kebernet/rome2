/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom;

import java.util.List;

public class AtomCategory extends AtomElement<AtomCategory> implements Extensible {
    private String term;
    private String scheme;
    private String label;
    private ExtensibleImpl extensions = new ExtensibleImpl();

    public AtomCategory clone() throws CloneNotSupportedException {
        AtomCategory category = super.clone();
        category.setLabel(getLabel());
        category.setScheme(getScheme());
        category.setTerm(getTerm());
        category.getExtensions().addAll(extensions.cloneExtensions());
        return category;
    }

    public AtomExtension getExtension(String uri) {
        return extensions.getExtension(uri);
    }

    public List<String> getExtensionURIs() {
        return extensions.getExtensionURIs();
    }

    public List<AtomExtension> getExtensions() {
        return extensions.getExtensions();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
