/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom;

import net.java.rome2.utils.NotNullElementsArrayList;

import java.util.List;

public class AtomFeed extends AtomSource {
    private List<AtomEntry> entries;

    public List<AtomEntry> getEntries() {
        return (entries != null) ? entries : (entries = new NotNullElementsArrayList<AtomEntry>());
    }

    public AtomFeed clone() throws CloneNotSupportedException {
        AtomFeed feed = (AtomFeed) super.clone();
        for (AtomEntry entry : getEntries()) {
            feed.getEntries().add(entry.clone());
        }
        return feed;
    }

}
