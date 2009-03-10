import junit.framework.TestCase;
import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomFeed;
import net.java.rome2.atom.io.AtomReader;
import net.java.rome2.atom.io.AtomReaderFactory;
import net.java.rome2.atom.io.AtomWriter;
import net.java.rome2.atom.io.AtomWriterFactory;
import net.java.rome2.impl.atom.spi.BagExtensionGenerator;
import net.java.rome2.impl.atom.spi.BagExtensionParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author tucu
 */
public class TestBagAtomReaderWriter extends TestCase {

    public void testBag() throws Exception {
        AtomReaderFactory readerFactory = AtomReaderFactory.createFactory();
        readerFactory.getExtensionParserFactory().registerParser("uri:bag/*", BagExtensionParser.class);
        AtomWriterFactory writerFactory = AtomWriterFactory.createFactory();
        writerFactory.getExtensionGeneratorFactory().registerGenerator("uri:bag/*", BagExtensionGenerator.class);

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStreamReader isr = new InputStreamReader(cl.getResourceAsStream("atombag.xml"));
        AtomReader reader = readerFactory.createReader(isr, isr.getEncoding());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(baos);
        AtomWriter writer = writerFactory.createWriter(osw, osw.getEncoding());
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
