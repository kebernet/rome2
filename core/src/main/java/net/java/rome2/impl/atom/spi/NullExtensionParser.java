package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomElement;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.Extensible;
import net.java.rome2.atom.spi.AtomParserContext;
import net.java.rome2.atom.spi.ExtensionParser;
import org.jdom.Element;

import java.util.List;


public class NullExtensionParser implements ExtensionParser {

    public void parse(AtomParserContext context, List jElements, Extensible aElement) throws AtomException {
    }


    public void parseAttributes(AtomParserContext context, Element jElement, List jAttributes, AtomElement aElement)
            throws AtomException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
