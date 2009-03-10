package net.java.rome2.atom.spi;

import net.java.rome2.impl.atom.spi.NullExtensionGenerator;
import net.java.rome2.impl.atom.spi.UnknownExtensionGenerator;

import java.util.List;

public class ExtensionGeneratorFactory {
    private URIClassRegistry registry = new URIClassRegistry(NullExtensionGenerator.class);

    public ExtensionGeneratorFactory() {
        registry.register("*", UnknownExtensionGenerator.class);
    }

    public List<String> getRegisteredURIs() {
        return registry.getRegisteredURIs();
    }

    public void registerGenerator(String uri, Class<? extends ExtensionGenerator> klass) {
        registry.register(uri, klass);
    }

    public void unregisterGenerator(String uri) {
        registry.unregister(uri);
    }

    public ExtensionGenerator createGenerator(String uri) {
        return (ExtensionGenerator) registry.createInstance(uri);
    }

}
