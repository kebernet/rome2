package net.java.rome2.atom.spi;

import net.java.rome2.impl.atom.spi.AtomGeneratorImpl;

public class AtomGeneratorFactory {

    public AtomGenerator createGenerator() {
        return new AtomGeneratorImpl();
    }

}