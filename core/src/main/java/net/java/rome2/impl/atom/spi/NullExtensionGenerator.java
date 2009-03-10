package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomElement;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomExtAttr;
import net.java.rome2.atom.AtomExtension;
import net.java.rome2.atom.Extensible;
import net.java.rome2.atom.spi.AtomGeneratorContext;
import net.java.rome2.atom.spi.ExtensionGenerator;
import org.jdom.Element;

import java.util.Set;

/**
 * @author tucu
 */
public class NullExtensionGenerator implements ExtensionGenerator<AtomExtension> {

    public boolean canGenerate(Class klass) {
        return true;
    }

    public void generate(AtomGeneratorContext context, Extensible aElement, AtomExtension aExtension, Element jElement)
            throws AtomException {
    }

    public void generateAttributes(AtomGeneratorContext context, AtomElement aElement, Set<AtomExtAttr> atomExtAttrs,
                                   Element jElement) throws AtomException {
    }


}
