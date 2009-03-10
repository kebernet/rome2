package net.java.rome2.atom;

public class TestAtomId extends BeanTestCase {

    public void testBean() {
        AtomId atomId = new AtomId();
        assertEquals(null, atomId.getId());
        atomId.setId("i");
        assertEquals("i", atomId.getId());
    }

    public void testClone() throws Exception {
        AtomId Id = new AtomId();
        Id.setId("i");
        AtomId clone = Id.clone();
        assertEquals(Id, clone);
    }

    public static void assertEquals(AtomId id1, AtomId id2) {
        if (notNull(id1, id2)) {
            TestAtomElement.assertEquals(id1, id2);
            assertEquals(id1.getId(), id2.getId());
        }
    }
}