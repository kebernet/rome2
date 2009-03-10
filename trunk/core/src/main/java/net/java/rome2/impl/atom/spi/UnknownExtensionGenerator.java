package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.Extensible;
import net.java.rome2.atom.UnknownAtomExtension;
import net.java.rome2.atom.spi.AtomGeneratorContext;
import org.jdom.Element;
import org.jdom.Namespace;

public class UnknownExtensionGenerator extends BaseExtensionGenerator<UnknownAtomExtension> {

    public boolean canGenerate(Class klass) {
        return klass == UnknownAtomExtension.class;
    }

    public void generate(AtomGeneratorContext context, Extensible aElement, UnknownAtomExtension aExtension,
                         Element jElement) throws AtomException {
        Namespace ns = context.getNamespace(aExtension.getPrefix(), aExtension.getUri());
        for (Element e : aExtension.getElements()) {
            e = (Element) e.clone();
            e.detach();
            e.setNamespace(ns);
            jElement.addContent(e);
        }
    }

}
