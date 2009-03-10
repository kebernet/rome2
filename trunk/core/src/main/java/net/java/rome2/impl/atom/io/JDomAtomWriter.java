package net.java.rome2.impl.atom.io;

import net.java.rome2.atom.AtomConstants;
import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomFeed;
import net.java.rome2.atom.io.AtomWriter;
import net.java.rome2.atom.io.AtomWriterFactory;
import net.java.rome2.atom.spi.AtomGenerator;
import net.java.rome2.atom.spi.AtomGeneratorContext;
import net.java.rome2.atom.spi.AtomGeneratorFactory;
import net.java.rome2.atom.spi.ExtensionGenerator;
import net.java.rome2.atom.spi.ExtensionGeneratorFactory;
import net.java.rome2.atom.spi.Reporter;
import net.java.rome2.impl.atom.spi.ReporterImpl;
import net.java.rome2.utils.DateUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class JDomAtomWriter extends ReporterImpl implements AtomWriter, AtomGeneratorContext {
    private Map<String, String> namespacePrefixes = new HashMap<String, String>();
    private Writer writer;
    private String encoding;
    private AtomGeneratorFactory generatorFactory;
    private AtomGenerator generator;
    private ExtensionGeneratorFactory extensionGeneratorFactory;
    private DateUtils dateUtils;

    private AtomFeed feed;
    private AtomEntry entry;
    private Element xmlFeed;
    private Element xmlEntry;
    private Element root;
    private int writtenEntries;
    private boolean closed;
    private Namespace atomNamespace;

    public JDomAtomWriter(Writer writer, String encoding, AtomWriterFactory atomWriterFactory) {
        namespacePrefixes.put(AtomConstants.ATOM_NS_URI, AtomConstants.ATOM_NS_DEFAULT_PREFIX);
        this.writer = writer;
        this.encoding = encoding;
        generatorFactory = atomWriterFactory.getAtomGeneratorFactory();
        generator = generatorFactory.createGenerator();
        this.extensionGeneratorFactory = atomWriterFactory.getExtensionGeneratorFactory();
        dateUtils = atomWriterFactory.getDateUtilsFactory().createDateUtils();
    }

    public DateUtils getDateUtils() {
        return dateUtils;
    }

    public Map<String, String> getNamespacePrefixMap() {
        return namespacePrefixes;
    }

    public String getEncoding() {
        return encoding;
    }

    public void writeFeed(AtomFeed feed) throws IOException, AtomException {
        if (closed) {
            throw new IOException("Writer has been closed");
        }
        if (this.feed != null) {
            throw new AtomException("Feed Atom doc, feed element already written out");
        }
        if (this.entry != null) {
            throw new AtomException("Entry Atom doc, entry element already written out");
        }
        this.feed = feed;
        xmlFeed = new Element("feed", getAtomNamespace());
        generator.generateFeed(this, this.feed, xmlFeed);
        root = xmlFeed;
    }

    public void writeEntry(AtomEntry entry) throws IOException, AtomException {
        if (closed) {
            throw new IOException("Writer has been closed");
        }
        if (this.feed == null && this.entry != null) {
            throw new AtomException("Entry Atom doc, entry element already written out");
        }
        this.entry = entry;
        writtenEntries++;
        xmlEntry = new Element("entry", getAtomNamespace());
        generator.generateEntry(this, this.entry, xmlEntry);
        if (xmlFeed != null) {
            xmlFeed.addContent(xmlEntry);
        }
        else {
            root = xmlEntry;
        }
    }

    public void close() throws IOException, AtomException {
        closed = true;
        outputJDOM();
        writer.close();
    }

    private void outputJDOM() throws IOException {
        for (Map.Entry<String, String> uriPrefix : getNamespacePrefixMap().entrySet()) {
            String uri = uriPrefix.getKey();
            String prefix = uriPrefix.getValue();
            Namespace ns = Namespace.getNamespace(prefix, uri);
            root.addNamespaceDeclaration(ns);
        }
        Format format = Format.getPrettyFormat();
        format.setEncoding(getEncoding());
        XMLOutputter outputter = new XMLOutputter(format);
        outputter.output(new Document(root), writer);
    }

    // ReporterImpl & AtomWriter

    public int getEntryCount() {
        return writtenEntries;
    }

    // AtomContext
    public Reporter getReporter() {
        return this;
    }

    public AtomFeed getFeed() {
        return feed;
    }

    public Element getXmlFeed() {
        return xmlFeed;
    }

    public Element getXmlEntry() {
        return xmlEntry;
    }

    public AtomEntry getEntry() {
        return entry;
    }

    // AtomGeneratorContext

    public AtomGenerator createGenerator() {
        return generatorFactory.createGenerator();
    }

    public Namespace getAtomNamespace() {
        if (atomNamespace == null) {
            String prefix = getNamespacePrefixMap().get(AtomConstants.ATOM_NS_URI);
            if (prefix == null) {
                if (getNamespacePrefixMap().containsValue("")) {
                    prefix = "atom";
                }
                else {
                    prefix = "";
                }
            }
            atomNamespace = getNamespace(prefix, AtomConstants.ATOM_NS_URI);
        }
        return atomNamespace;
    }

    public Namespace getNamespace(String preferredPrefix, String uri) {
        String prefix = getNamespacePrefixMap().get(uri);
        if (prefix == null) {
            prefix = generatePrefix(preferredPrefix);
            getNamespacePrefixMap().put(uri, prefix);
        }
        return Namespace.getNamespace(prefix, uri);
    }

    private String generatePrefix(String preferredPrefix) {
        int counter = 0;
        String prefix = preferredPrefix;
        if (prefix == null) {
            preferredPrefix = "ns";
        }
        boolean found = getNamespacePrefixMap().containsValue(prefix);
        while (found || prefix == null) {
            prefix = preferredPrefix + counter++;
            found = getNamespacePrefixMap().containsValue(prefix);
        }
        return prefix;
    }

    public ExtensionGenerator createExtensionGenerator(String uri) {
        return extensionGeneratorFactory.createGenerator(uri);
    }

}
