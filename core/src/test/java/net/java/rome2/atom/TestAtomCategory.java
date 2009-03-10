package net.java.rome2.atom;

public class TestAtomCategory extends BeanTestCase {

    public void testBean() {
        AtomCategory category = new AtomCategory();
        assertEquals(null, category.getLabel());
        assertEquals(null, category.getScheme());
        assertEquals(null, category.getTerm());
        assertEquals(0, category.getExtensions().size());
        category.setLabel("l");
        category.setScheme("s");
        category.setTerm("t");
        assertEquals("l", category.getLabel());
        assertEquals("s", category.getScheme());
        assertEquals("t", category.getTerm());
    }

    public void testClone() throws Exception {
        AtomCategory category = new AtomCategory();
        category.setLabel("l");
        category.setScheme("s");
        category.setTerm("t");
        category.getExtensions().add(new UnknownAtomExtension("a", "A"));
        assertEquals(category, category.clone());
    }

    public static void assertEquals(AtomCategory category1, AtomCategory category2) {
        if (notNull(category1, category2)) {
            TestAtomElement.assertEquals(category1, category2);
            assertEquals(category1.getLabel(), category2.getLabel());
            assertEquals(category1.getScheme(), category2.getScheme());
            assertEquals(category1.getTerm(), category2.getTerm());
            TestExtensibleImpl.assertEquals(category1, category2);
        }
    }
}