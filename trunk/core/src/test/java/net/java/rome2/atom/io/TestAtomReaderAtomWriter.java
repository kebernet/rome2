package net.java.rome2.atom.io;

import junit.framework.TestCase;
import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomFeed;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TestAtomReaderAtomWriter extends TestCase {

    public void testAtomReader() throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStreamReader isr = new InputStreamReader(cl.getResourceAsStream("atom.xml"));
        AtomReader reader = AtomReaderFactory.createFactory().createReader(isr, isr.getEncoding());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(baos);
        AtomWriter writer = AtomWriterFactory.createFactory().createWriter(osw, osw.getEncoding());
        AtomFeed feed = reader.readFeed();
        writer.writeFeed(feed);
        AtomEntry entry = reader.readEntry();
        while (entry != null) {
            writer.writeEntry(entry);
            entry = reader.readEntry();
        }
        reader.close();
        writer.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
        br.close();
    }


}
