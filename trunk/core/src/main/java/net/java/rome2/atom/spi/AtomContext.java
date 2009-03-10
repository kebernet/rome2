package net.java.rome2.atom.spi;

import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomFeed;
import net.java.rome2.utils.DateUtils;
import org.jdom.Element;
import org.jdom.Namespace;

import java.util.Map;

public interface AtomContext {

    Map<String, String> getNamespacePrefixMap();

    Reporter getReporter();

    Element getXmlFeed();

    Element getXmlEntry();

    AtomFeed getFeed();

    AtomEntry getEntry();

    Namespace getAtomNamespace();

    DateUtils getDateUtils();
}
