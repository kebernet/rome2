package net.java.rome2.atom.spi;

import net.java.rome2.impl.atom.spi.NullExtensionParser;
import net.java.rome2.impl.atom.spi.UnknownExtensionParser;

import java.util.List;

public class ExtensionParserFactory {
    private URIClassRegistry registry = new URIClassRegistry(NullExtensionParser.class);

    public ExtensionParserFactory() {
        registry.register("*", UnknownExtensionParser.class);
    }

    public List<String> getRegisteredURIs() {
        return registry.getRegisteredURIs();
    }

    public void registerParser(String uri, Class<? extends ExtensionParser> klass) {
        registry.register(uri, klass);
    }

    public void unregisterParser(String uri) {
        registry.unregister(uri);
    }

    public ExtensionParser createParser(String uri) {
        return (ExtensionParser) registry.createInstance(uri);
    }

}
