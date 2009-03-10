/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom;

public class AtomLink extends AtomElement<AtomLink> {
    private String href;
    private String rel;
    private String type;
    private String hreflang;
    private String title;
    private long length = -1;

    public AtomLink clone() throws CloneNotSupportedException {
        AtomLink link = super.clone();
        link.setHref(getHref());
        link.setHreflang(getHreflang());
        link.setLength(getLength());
        link.setRel(getRel());
        link.setTitle(getTitle());
        link.setType(getType());
        return link;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHreflang() {
        return hreflang;
    }

    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long lenght) {
        this.length = lenght;
    }
}
