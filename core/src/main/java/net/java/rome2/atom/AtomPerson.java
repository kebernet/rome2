/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom;

import java.util.List;

public class AtomPerson extends AtomElement<AtomPerson> implements Extensible {
    private String name;
    private String uri;
    private String email;
    private ExtensibleImpl extensions = new ExtensibleImpl();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AtomPerson clone() throws CloneNotSupportedException {
        AtomPerson person = super.clone();
        person.setName(getName());
        person.setUri(getUri());
        person.setEmail(getEmail());
        person.getExtensions().addAll(extensions.cloneExtensions());
        return person;
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

}
