package net.java.rome2.atom.spi;

import net.java.rome2.impl.atom.spi.AtomParserImpl;

public class AtomParserFactory {

    public AtomParser createParser() {
        return new AtomParserImpl();
    }

}
