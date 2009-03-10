package net.java.rome2.atom.spi;

import net.java.rome2.atom.AtomElement;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomExtAttr;
import net.java.rome2.atom.AtomExtension;
import net.java.rome2.atom.Extensible;
import org.jdom.Element;

import java.util.Set;

public interface ExtensionGenerator<E extends AtomExtension> {

    boolean canGenerate(Class<? extends AtomExtension> klass);

    void generate(AtomGeneratorContext context, Extensible aElement, E aExtension, Element jElement)
            throws AtomException;

    void generateAttributes(AtomGeneratorContext context, AtomElement aElement, Set<AtomExtAttr> aAtomExtAttrs,
                            Element jElement) throws AtomException;

}
