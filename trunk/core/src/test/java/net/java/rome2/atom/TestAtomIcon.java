package net.java.rome2.atom;

public class TestAtomIcon extends BeanTestCase {

    public void testBean() {
        AtomIcon atomIcon = new AtomIcon();
        assertEquals(null, atomIcon.getIcon());
        atomIcon.setIcon("i");
        assertEquals("i", atomIcon.getIcon());
    }

    public void testClone() throws Exception {
        AtomIcon Icon = new AtomIcon();
        Icon.setIcon("i");
        AtomIcon clone = Icon.clone();
        assertEquals(Icon, clone);
    }

    public static void assertEquals(AtomIcon icon1, AtomIcon icon2) {
        if (notNull(icon1, icon2)) {
            TestAtomElement.assertEquals(icon1, icon2);
            assertEquals(icon1.getIcon(), icon2.getIcon());
        }
    }
}