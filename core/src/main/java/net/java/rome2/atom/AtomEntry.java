/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom;

import net.java.rome2.utils.NotNullElementsArrayList;

import java.util.List;

public class AtomEntry extends AtomElement<AtomEntry> implements Extensible {
    private List<AtomPerson> authors;
    private List<AtomCategory> categories;
    private AtomContent content;
    private List<AtomPerson> contributors;
    private AtomId id;
    private List<AtomLink> links;
    private AtomDate published;
    private AtomText rights;
    private AtomSource source;
    private AtomText summary;
    private AtomText title;
    private AtomDate updated;
    private ExtensibleImpl extensions = new ExtensibleImpl();

    public List<AtomPerson> getAuthors() {
        return (authors != null) ? authors : (authors = new NotNullElementsArrayList<AtomPerson>());
    }

    public List<AtomCategory> getCategories() {
        return (categories != null) ? categories : (categories = new NotNullElementsArrayList<AtomCategory>());
    }

    public AtomContent getContent() {
        return content;
    }

    public void setContent(AtomContent content) {
        this.content = content;
    }

    public List<AtomPerson> getContributors() {
        return (contributors != null) ? contributors : (contributors = new NotNullElementsArrayList<AtomPerson>());
    }

    public AtomId getId() {
        return id;
    }

    public void setId(AtomId id) {
        this.id = id;
    }

    public List<AtomLink> getLinks() {
        return (links != null) ? links : (links = new NotNullElementsArrayList<AtomLink>());
    }

    public AtomDate getPublished() {
        return published;
    }

    public void setPublished(AtomDate published) {
        this.published = published;
    }

    public AtomText getRights() {
        return rights;
    }

    public void setRights(AtomText rights) {
        this.rights = rights;
    }

    public AtomSource getSource() {
        return source;
    }

    public void setSource(AtomSource source) {
        this.source = source;
    }

    public AtomText getSummary() {
        return summary;
    }

    public void setSummary(AtomText summary) {
        this.summary = summary;
    }

    public AtomText getTitle() {
        return title;
    }

    public void setTitle(AtomText title) {
        this.title = title;
    }

    public AtomDate getUpdated() {
        return updated;
    }

    public void setUpdated(AtomDate updated) {
        this.updated = updated;
    }

    public AtomEntry clone() throws CloneNotSupportedException {
        AtomEntry entry = super.clone();
        for (AtomPerson p : getAuthors()) {
            entry.getAuthors().add(p.clone());
        }
        for (AtomCategory c : getCategories()) {
            entry.getCategories().add(c.clone());
        }
        if (getContent() != null) {
            entry.setContent(getContent().clone());
        }
        for (AtomPerson p : getContributors()) {
            entry.getContributors().add(p.clone());
        }
        if (getId() != null) {
            entry.setId(getId().clone());
        }
        for (AtomLink l : getLinks()) {
            entry.getLinks().add(l.clone());
        }
        if (getPublished() != null) {
            entry.setPublished(getPublished().clone());
        }
        if (getRights() != null) {
            entry.setRights(getRights().clone());
        }
        if (getSource() != null) {
            entry.setSource(getSource().clone());
        }
        if (getSummary() != null) {
            entry.setSummary(getSummary().clone());
        }
        if (getTitle() != null) {
            entry.setTitle(getTitle().clone());
        }
        if (getUpdated() != null) {
            entry.setUpdated(getUpdated().clone());
        }
        entry.getExtensions().addAll(extensions.cloneExtensions());
        return entry;
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
