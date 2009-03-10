package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.Extensible;
import net.java.rome2.atom.extension.bag.BagAtomExtension;
import net.java.rome2.atom.spi.AtomGeneratorContext;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Namespace;

public class BagExtensionGenerator extends BaseExtensionGenerator<BagAtomExtension> {

    public boolean canGenerate(Class klass) {
        return klass == BagAtomExtension.class;
    }

    public void generate(AtomGeneratorContext context, Extensible aElement, BagAtomExtension aExtension, Element jElement)
            throws AtomException {
        Namespace ns = context.getNamespace(aExtension.getPrefix(), aExtension.getUri());
        for (String name : aExtension.getNames()) {
            for (Object value : aExtension.getValues(name)) {
                Element e = new Element(name, ns);
                e.setContent(generateContent(context, aExtension, name, value));
                jElement.addContent(e);
            }
        }
    }

    protected Content generateContent(AtomGeneratorContext context, BagAtomExtension extension, String name,
                                      Object object) {
        return new org.jdom.Text(object.toString());
    }

}