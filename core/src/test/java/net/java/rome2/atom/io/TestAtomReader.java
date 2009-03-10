package net.java.rome2.atom.io;

import junit.framework.TestCase;
import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomFeed;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestAtomReader extends TestCase {

    public void testAtomReader() throws Exception {
        List<String> errors = new ArrayList<String>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStreamReader isr = new InputStreamReader(cl.getResourceAsStream("atom.xml"));
        AtomReader reader = AtomReaderFactory.createFactory().createReader(isr, isr.getEncoding());
        reader.setReportingList(errors);
        AtomFeed feed = reader.readFeed();
        System.out.println(feed);
        AtomEntry entry = reader.readEntry();
        while (entry != null) {
            System.out.println(entry);
            entry = reader.readEntry();
        }
        reader.close();
        for (String e : errors) {
            System.out.println(e);
        }
    }
}
