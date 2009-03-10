package net.java.rome2.atom.spi;

import net.java.rome2.atom.AtomElement;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomExtension;
import net.java.rome2.atom.Extensible;
import org.jdom.Attribute;
import org.jdom.Element;

import java.util.List;

public interface ExtensionParser<E extends AtomExtension> {

    void parse(AtomParserContext context, List<Element> jElements, Extensible aElement) throws AtomException;

    // for extension attributes on atom elements
    void parseAttributes(AtomParserContext context, Element jElement, List<Attribute> jAttributes, AtomElement aElement)
            throws AtomException;
}
