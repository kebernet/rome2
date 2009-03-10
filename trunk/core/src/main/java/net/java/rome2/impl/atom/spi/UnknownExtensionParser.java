package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.Extensible;
import net.java.rome2.atom.UnknownAtomExtension;
import net.java.rome2.atom.spi.AtomParserContext;
import org.jdom.Element;

import java.util.List;

public class UnknownExtensionParser extends BaseExtensionParser<UnknownAtomExtension> {

    public void parse(AtomParserContext context, List<Element> jElements, Extensible aElement) throws AtomException {
        Element first = jElements.get(0);
        UnknownAtomExtension ext = new UnknownAtomExtension(first.getNamespacePrefix(), first.getNamespaceURI());
        for (Element e : jElements) {
            e = (Element) e.clone();
            e.detach();
            ext.getElements().add(e);
        }
        aElement.getExtensions().add(ext);
    }

}
