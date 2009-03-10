package net.java.rome2.atom;

import java.util.List;

public interface Extensible {

    public AtomExtension getExtension(String uri);

    public List<String> getExtensionURIs();

    public List<AtomExtension> getExtensions();

}
