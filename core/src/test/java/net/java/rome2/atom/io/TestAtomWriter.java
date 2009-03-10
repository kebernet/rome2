package net.java.rome2.atom.io;

import junit.framework.TestCase;
import net.java.rome2.atom.AtomContent;
import net.java.rome2.atom.AtomDate;
import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomFeed;
import net.java.rome2.atom.AtomLink;
import net.java.rome2.atom.AtomText;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

public class TestAtomWriter extends TestCase {

    public void testAtomWriter() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStreamWriter osr = new OutputStreamWriter(os, "UTF-8");
        AtomWriter writer = AtomWriterFactory.createFactory().createWriter(osr, osr.getEncoding());
        AtomFeed feed = new AtomFeed();
        feed.setTitle(new AtomText());
        feed.getTitle().setText("feed.title");
        feed.setXmlLang("en");
        writer.writeFeed(feed);

        AtomEntry entry = new AtomEntry();
        entry.setTitle(new AtomText());
        entry.getTitle().setHtml("entry.title");
        entry.getTitle().setXmlBase("XXXXXXXXXXXXXXX");
        entry.setXmlLang("es");
        entry.getLinks().add(new AtomLink());
        entry.getLinks().get(0).setHref("http://foo.com");
        entry.getLinks().get(0).setRel("alternate");
        entry.getLinks().get(0).setType("text/html");
        entry.setPublished(new AtomDate());
        entry.getPublished().setDate(new Date());
        entry.setExtensionAttribute("hello", "xURI", "x", "X");

        AtomContent content = new AtomContent();
        content.setType("application/bin");

        content.setBinary(new byte[1000]);

        entry.setContent(content);

        writer.writeEntry(entry);

        entry.setXmlLang("fr");
        writer.writeEntry(entry);
        writer.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(os.toByteArray())));
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
        br.close();
    }
}
