/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom.io;

import net.java.rome2.atom.spi.AtomParserFactory;
import net.java.rome2.atom.spi.DateUtilsFactory;
import net.java.rome2.atom.spi.ExtensionParserFactory;
import net.java.rome2.impl.atom.io.JDomAtomReader;

import java.io.Reader;

public class AtomReaderFactory {
    private static Class<? extends AtomReaderFactory> factoryKlass = AtomReaderFactory.class;

    private AtomParserFactory atomParserFactory;
    private ExtensionParserFactory extensionParserFactory;
    private DateUtilsFactory dateUtilsFactory;


    public static void setFactoryClass(Class<? extends AtomReaderFactory> factoryClass) {
        factoryKlass = factoryClass;
    }

    public static AtomReaderFactory createFactory() {
        try {
            return factoryKlass.newInstance();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected AtomReaderFactory() {
        setAtomParserFactory(new AtomParserFactory());
        setExtensionParserFactory(new ExtensionParserFactory());
        setDateUtilsFactory(new DateUtilsFactory());
    }

    public AtomParserFactory getAtomParserFactory() {
        return atomParserFactory;
    }

    public void setAtomParserFactory(AtomParserFactory atomParserFactory) {
        this.atomParserFactory = atomParserFactory;
    }

    public ExtensionParserFactory getExtensionParserFactory() {
        return extensionParserFactory;
    }

    public void setExtensionParserFactory(ExtensionParserFactory extensionParserFactory) {
        this.extensionParserFactory = extensionParserFactory;
    }

    public DateUtilsFactory getDateUtilsFactory() {
        return dateUtilsFactory;
    }

    public void setDateUtilsFactory(DateUtilsFactory dateUtilsFactory) {
        this.dateUtilsFactory = dateUtilsFactory;
    }

    public AtomReader createReader(Reader reader, String encoding) {
        try {
            return new JDomAtomReader(reader, encoding, this);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
