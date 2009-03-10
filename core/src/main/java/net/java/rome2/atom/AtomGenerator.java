/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom;

public class AtomGenerator extends AtomElement<AtomGenerator> {
    private String uri;
    private String version;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AtomGenerator clone() throws CloneNotSupportedException {
        AtomGenerator generator = super.clone();
        generator.setUri(getUri());
        generator.setVersion(getVersion());
        generator.setText(getText());
        return generator;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
