package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.Extensible;
import net.java.rome2.atom.extension.bag.BagAtomExtension;
import net.java.rome2.atom.spi.AtomParserContext;
import org.jdom.Element;

import java.util.List;

public class BagExtensionParser extends BaseExtensionParser<BagAtomExtension> {

    public void parse(AtomParserContext context, List<Element> jElements, Extensible aElement) throws AtomException {
        Element first = jElements.get(0);
        BagAtomExtension ext = new BagAtomExtension(first.getNamespaceURI(), first.getNamespacePrefix());
        for (Element e : jElements) {
            ext.add(e.getName(), parseElement(context, ext, e.getName(), e));
        }
        aElement.getExtensions().add(ext);
    }

    protected Object parseElement(AtomParserContext context, BagAtomExtension extension, String name, Element e) {
        return e.getText();
    }

}