/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom.io;

import net.java.rome2.atom.spi.AtomGeneratorFactory;
import net.java.rome2.atom.spi.DateUtilsFactory;
import net.java.rome2.atom.spi.ExtensionGeneratorFactory;
import net.java.rome2.impl.atom.io.JDomAtomWriter;

import java.io.Writer;

public class AtomWriterFactory {
    private static Class<? extends AtomWriterFactory> factoryKlass = AtomWriterFactory.class;

    private AtomGeneratorFactory atomGeneratorFactory;
    private ExtensionGeneratorFactory extensionGeneratorFactory;
    private DateUtilsFactory dateUtilsFactory;

    public static void setFactoryClass(Class<? extends AtomWriterFactory> factoryClass) {
        factoryKlass = factoryClass;
    }

    public static AtomWriterFactory createFactory() {
        try {
            return factoryKlass.newInstance();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected AtomWriterFactory() {
        setAtomGeneratorFactory(new AtomGeneratorFactory());
        setExtensionGeneratorFactory(new ExtensionGeneratorFactory());
        setDateUtilsFactory(new DateUtilsFactory());
    }

    public AtomGeneratorFactory getAtomGeneratorFactory() {
        return atomGeneratorFactory;
    }

    public void setAtomGeneratorFactory(AtomGeneratorFactory atomGeneratorFactory) {
        this.atomGeneratorFactory = atomGeneratorFactory;
    }

    public ExtensionGeneratorFactory getExtensionGeneratorFactory() {
        return extensionGeneratorFactory;
    }

    public void setExtensionGeneratorFactory(ExtensionGeneratorFactory extensionGeneratorFactory) {
        this.extensionGeneratorFactory = extensionGeneratorFactory;
    }

    public DateUtilsFactory getDateUtilsFactory() {
        return dateUtilsFactory;
    }

    public void setDateUtilsFactory(DateUtilsFactory dateUtilsFactory) {
        this.dateUtilsFactory = dateUtilsFactory;
    }

    public AtomWriter createWriter(Writer writer, String encoding) {
        try {
            return new JDomAtomWriter(writer, encoding, this);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
