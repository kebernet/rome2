package net.java.rome2.atom;

import junit.framework.TestCase;

import java.util.Date;

public class TestAtomFeed extends BeanTestCase {

    public void testSource() throws Exception {
        AtomFeed feed = new AtomFeed();
        assertEquals(0, feed.getEntries().size());

        feed.getEntries().add(new AtomEntry());
        assertEquals(1, feed.getEntries().size());
    }

    public void testClone() throws Exception {
        AtomFeed feed = new AtomFeed();

        TestAtomSource.seedSource(feed);

        assertEquals(feed, feed.clone());
    }


    public static void assertEquals(AtomFeed feed1, AtomFeed feed2) {
        if (notNull(feed1, feed2)) {
            TestAtomSource.assertEquals(feed1, feed2);
            assertListEquals(feed1.getEntries(), feed2.getEntries(), TestAtomEntry.class, AtomEntry.class);
        }
    }

}
