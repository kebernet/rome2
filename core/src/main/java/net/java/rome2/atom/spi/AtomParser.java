package net.java.rome2.atom.spi;

import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomFeed;
import org.jdom.Element;

public interface AtomParser {

    void parseFeed(AtomParserContext context, Element jFeed, AtomFeed aFeed) throws AtomException;

    void parseEntry(AtomParserContext context, Element jEntry, AtomEntry aEntry) throws AtomException;
}
