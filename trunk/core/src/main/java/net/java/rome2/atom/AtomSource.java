package net.java.rome2.atom;

import net.java.rome2.utils.NotNullElementsArrayList;

import java.util.List;

public class AtomSource extends AtomElement<AtomSource> implements Extensible {
    private List<AtomPerson> authors;
    private List<AtomCategory> categories;
    private List<AtomPerson> contributors;
    private AtomGenerator generator;
    private AtomIcon icon;
    private AtomLogo logo;
    private AtomId id;
    private List<AtomLink> links;
    private AtomText rights;
    private AtomText title;
    private AtomText subtitle;
    private AtomDate updated;
    private ExtensibleImpl extensions = new ExtensibleImpl();

    public List<AtomPerson> getAuthors() {
        return (authors != null) ? authors : (authors = new NotNullElementsArrayList<AtomPerson>());
    }

    public List<AtomCategory> getCategories() {
        return (categories != null) ? categories : (categories = new NotNullElementsArrayList<AtomCategory>());
    }

    public List<AtomPerson> getContributors() {
        return (contributors != null) ? contributors : (contributors = new NotNullElementsArrayList<AtomPerson>());
    }

    public AtomGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(AtomGenerator generator) {
        this.generator = generator;
    }

    public AtomIcon getIcon() {
        return icon;
    }

    public void setIcon(AtomIcon icon) {
        this.icon = icon;
    }

    public AtomLogo getLogo() {
        return logo;
    }

    public void setLogo(AtomLogo logo) {
        this.logo = logo;
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

    public AtomText getRights() {
        return rights;
    }

    public void setRights(AtomText rights) {
        this.rights = rights;
    }

    public AtomText getTitle() {
        return title;
    }

    public void setTitle(AtomText title) {
        this.title = title;
    }

    public AtomText getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(AtomText subtitle) {
        this.subtitle = subtitle;
    }

    public AtomDate getUpdated() {
        return updated;
    }

    public void setUpdated(AtomDate updated) {
        this.updated = updated;
    }

    public AtomSource clone() throws CloneNotSupportedException {
        AtomSource source = super.clone();
        for (AtomPerson p : getAuthors()) {
            source.getAuthors().add(p.clone());
        }
        for (AtomCategory c : getCategories()) {
            source.getCategories().add(c.clone());
        }
        for (AtomPerson p : getContributors()) {
            source.getContributors().add(p.clone());
        }
        if (getGenerator() != null) {
            source.setGenerator(getGenerator().clone());
        }
        if (getId() != null) {
            source.setId(getId().clone());
        }
        if (getIcon() != null) {
            source.setIcon(getIcon().clone());
        }
        if (getLogo() != null) {
            source.setLogo(getLogo().clone());
        }
        for (AtomLink l : getLinks()) {
            source.getLinks().add(l.clone());
        }
        if (getRights() != null) {
            source.setRights(getRights().clone());
        }
        if (getTitle() != null) {
            source.setTitle(getTitle().clone());
        }
        if (getSubtitle() != null) {
            source.setSubtitle(getSubtitle().clone());
        }
        if (getUpdated() != null) {
            source.setUpdated(getUpdated().clone());
        }
        source.getExtensions().addAll(extensions.cloneExtensions());
        return source;
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
