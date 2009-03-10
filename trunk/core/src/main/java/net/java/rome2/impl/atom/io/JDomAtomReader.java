package net.java.rome2.impl.atom.io;

import net.java.rome2.atom.AtomConstants;
import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomFeed;
import net.java.rome2.atom.io.AtomReader;
import net.java.rome2.atom.io.AtomReaderFactory;
import net.java.rome2.atom.spi.AtomParser;
import net.java.rome2.atom.spi.AtomParserContext;
import net.java.rome2.atom.spi.AtomParserFactory;
import net.java.rome2.atom.spi.ExtensionParser;
import net.java.rome2.atom.spi.ExtensionParserFactory;
import net.java.rome2.atom.spi.Reporter;
import net.java.rome2.impl.atom.spi.ReporterImpl;
import net.java.rome2.utils.DateUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;

import javax.xml.XMLConstants;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JDomAtomReader extends ReporterImpl implements AtomReader, AtomParserContext {
    private Namespace atomNamespace;
    private Map<String, String> namespacePrefixes = new HashMap<String, String>();
    private Reader reader;
    private String encoding;
    private AtomParserFactory parserFactory;
    private AtomParser parser;
    private ExtensionParserFactory extensionParserFactory;
    private DateUtils dateUtils;

    private Element root;
    private Element xmlFeed;
    private Element xmlEntry;
    private AtomFeed feed;
    private AtomEntry entry;
    private int readEntries;
    private Iterator<Element> entriesIterator;
    private boolean closed;

    public JDomAtomReader(Reader reader, String encoding, AtomReaderFactory atomReaderFactory) {
        this.reader = reader;
        this.encoding = encoding;
        parserFactory = atomReaderFactory.getAtomParserFactory();
        parser = parserFactory.createParser();
        extensionParserFactory = atomReaderFactory.getExtensionParserFactory();
        dateUtils = atomReaderFactory.getDateUtilsFactory().createDateUtils();
    }

    public DateUtils getDateUtils() {
        return dateUtils;
    }

    public Namespace getAtomNamespace() {
        return atomNamespace;
    }

    public Map<String, String> getNamespacePrefixMap() {
        return namespacePrefixes;
    }

    public String getEncoding() {
        return encoding;
    }

    public AtomFeed readFeed() throws IOException, AtomException {
        if (root == null) {
            loadAtomJDOM();
        }
        return feed;
    }

    public AtomEntry readEntry() throws IOException, AtomException {
        if (closed) {
            throw new IOException("Reader has been closed");
        }
        entry = null;
        if (root == null) {
            loadAtomJDOM();
        }
        if (entriesIterator.hasNext()) {
            readEntries++;
            xmlEntry = entriesIterator.next();
            entry = new AtomEntry();
            parser.parseEntry(this, xmlEntry, entry);
        }
        return entry;
    }

    public void close() throws IOException {
        closed = true;
        reader.close();
    }

    @SuppressWarnings({"unchecked"})
    private void loadAtomJDOM() throws IOException, AtomException {
        namespacePrefixes.put(AtomConstants.XML_NS_URI, XMLConstants.XML_NS_PREFIX);
        SAXBuilder saxBuilder = JDomUtils.createSAXBuilder();
        try {
            Document document = saxBuilder.build(reader);
            root = document.getRootElement();
        }
        catch (JDOMParseException ex) {
            throw new AtomException(ex);
        }
        catch (JDOMException ex) {
            throw new AtomException(ex);
        }
        if (!AtomConstants.ATOM_NS_URI.equals(root.getNamespaceURI())) {
            throw new AtomException("The document root does not belong to Atom namespace");
        }
        atomNamespace = root.getNamespace();
        namespacePrefixes.put(AtomConstants.ATOM_NS_URI, root.getNamespacePrefix());
        if (root.getName().equals("feed")) {
            xmlFeed = root;
            entriesIterator = ((List<Element>) root.getChildren("entry", getAtomNamespace())).iterator();
            root.removeChildren("entry");
            feed = new AtomFeed();
            parser.parseFeed(this, root, feed);
        }
        else if (root.getName().equals("entry")) {
            xmlFeed = null;
            List<Element> list = new ArrayList<Element>(1);
            list.add(root);
            entriesIterator = list.iterator();
        }
        else {
            throw new AtomException("Invalid Atom root element '" + root.getName() + "'");
        }
    }

    // ReporterImpl & AtomReader

    public int getEntryCount() {
        return readEntries;
    }

    // AtomParserContext

    public AtomParser createParser() {
        return parserFactory.createParser();
    }

    public Reporter getReporter() {
        return this;
    }

    public ExtensionParser createExtensionParser(String uri) {
        return extensionParserFactory.createParser(uri);
    }

    public Element getXmlFeed() {
        return xmlFeed;
    }

    public Element getXmlEntry() {
        return xmlEntry;
    }

    public AtomFeed getFeed() {
        return feed;
    }

    public AtomEntry getEntry() {
        return entry;
    }
}
