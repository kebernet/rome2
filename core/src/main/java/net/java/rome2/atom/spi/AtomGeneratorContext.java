package net.java.rome2.atom.spi;

import org.jdom.Namespace;

public interface AtomGeneratorContext extends AtomContext {

    AtomGenerator createGenerator();

    ExtensionGenerator createExtensionGenerator(String uri);

    Namespace getNamespace(String preferredPrefix, String uri);

}
