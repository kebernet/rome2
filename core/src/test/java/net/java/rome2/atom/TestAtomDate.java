package net.java.rome2.atom;

import java.util.Date;

public class TestAtomDate extends BeanTestCase {

    public void testBean() {
        AtomDate atomDate = new AtomDate();
        assertEquals(null, atomDate.getDate());
        Date date = new Date();
        atomDate.setDate(date);
        assertEquals(date, atomDate.getDate());
    }

    public void testClone() throws Exception {
        AtomDate atomDate = new AtomDate();
        atomDate.setDate(new Date());
        AtomDate clone = atomDate.clone();
        assertEquals(atomDate, clone);
        atomDate.getDate().setTime(System.currentTimeMillis());
        assertNotSame(atomDate.getDate(), clone.getDate());
    }

    public static void assertEquals(AtomDate atomDate1, AtomDate atomDate2) {
        if (notNull(atomDate1, atomDate1)) {
            TestAtomElement.assertEquals(atomDate1, atomDate2);
            assertEquals(atomDate1.getDate(), atomDate2.getDate());
        }
    }
}
