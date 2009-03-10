package net.java.rome2.atom;

public class TestAtomLink extends BeanTestCase {

    public void testBean() {
        AtomLink link = new AtomLink();
        assertEquals(null, link.getHref());
        assertEquals(null, link.getHreflang());
        assertEquals(-1, link.getLength());
        assertEquals(null, link.getRel());
        assertEquals(null, link.getTitle());
        assertEquals(null, link.getType());
        link.setHref("h");
        link.setHreflang("l");
        link.setLength(1);
        link.setRel("r");
        link.setTitle("t");
        link.setType("y");
        assertEquals("h", link.getHref());
        assertEquals("l", link.getHreflang());
        assertEquals(1, link.getLength());
        assertEquals("r", link.getRel());
        assertEquals("t", link.getTitle());
        assertEquals("y", link.getType());
    }

    public void testClone() throws Exception {
        AtomLink link = new AtomLink();
        link.setHref("h");
        link.setHreflang("l");
        link.setLength(1);
        link.setRel("r");
        link.setTitle("t");
        link.setType("y");
        assertEquals(link, link.clone());
    }

    public static void assertEquals(AtomLink link1, AtomLink link2) {
        if (notNull(link1, link2)) {
            TestAtomElement.assertEquals(link1, link2);
            assertEquals(link1.getHref(), link2.getHref());
            assertEquals(link1.getHreflang(), link2.getHreflang());
            assertEquals(link1.getLength(), link2.getLength());
            assertEquals(link1.getRel(), link2.getRel());
            assertEquals(link1.getTitle(), link2.getTitle());
            assertEquals(link1.getType(), link2.getType());
        }
    }
}