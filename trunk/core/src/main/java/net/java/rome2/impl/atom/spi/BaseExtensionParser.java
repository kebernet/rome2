package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomElement;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomExtension;
import net.java.rome2.atom.spi.AtomParserContext;
import net.java.rome2.atom.spi.ExtensionParser;
import org.jdom.Attribute;
import org.jdom.Element;

import java.util.List;

/**
 * @author tucu
 */
public abstract class BaseExtensionParser<E extends AtomExtension> implements ExtensionParser<E> {

    public void parseAttributes(AtomParserContext context, Element jElement, List<Attribute> jAttributes,
                                AtomElement aElement) throws AtomException {
        for (Attribute eAttr : jAttributes) {
            aElement.setExtensionAttribute(eAttr.getNamespacePrefix(), eAttr.getNamespaceURI(), eAttr.getName(),
                                           eAttr.getValue());
        }
    }

}
