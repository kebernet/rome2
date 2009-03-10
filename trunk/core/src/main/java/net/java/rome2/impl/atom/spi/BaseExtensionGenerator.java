package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomElement;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomExtAttr;
import net.java.rome2.atom.AtomExtension;
import net.java.rome2.atom.spi.AtomGeneratorContext;
import net.java.rome2.atom.spi.ExtensionGenerator;
import org.jdom.Element;
import org.jdom.Namespace;

import java.util.Set;

public abstract class BaseExtensionGenerator<E extends AtomExtension> implements ExtensionGenerator<E> {

    public void generateAttributes(AtomGeneratorContext context, AtomElement aElement, Set<AtomExtAttr> aAtomExtAttrs,
                                   Element jElement) throws AtomException {
        Namespace ns = null;
        for (AtomExtAttr attr : aAtomExtAttrs) {
            if (ns == null) {
                ns = context.getNamespace(attr.getPrefix(), attr.getNamespace());
            }
            jElement.setAttribute(attr.getName(), attr.getValue(), ns);
        }
    }

}