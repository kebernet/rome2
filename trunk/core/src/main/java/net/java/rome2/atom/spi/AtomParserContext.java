package net.java.rome2.atom.spi;

public interface AtomParserContext extends AtomContext {

    AtomParser createParser();

    ExtensionParser createExtensionParser(String uri);

}
