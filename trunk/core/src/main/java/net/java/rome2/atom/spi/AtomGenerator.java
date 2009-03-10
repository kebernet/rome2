package net.java.rome2.atom.spi;

import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomFeed;
import org.jdom.Element;

public interface AtomGenerator {

    void generateFeed(AtomGeneratorContext context, AtomFeed aFeed, Element jFeed) throws AtomException;

    void generateEntry(AtomGeneratorContext context, AtomEntry aEntry, Element jEntry) throws AtomException;

}
