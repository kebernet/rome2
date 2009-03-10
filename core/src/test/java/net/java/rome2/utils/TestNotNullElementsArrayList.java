package net.java.rome2.utils;

import junit.framework.TestCase;

import java.util.List;

public class TestNotNullElementsArrayList extends TestCase {

    public void testAdd() {
        List l = new NotNullElementsArrayList();
        l.add(new Object());
        assertEquals(1, l.size());
        assertNotNull(l.get(0));
        try {
            l.add(null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    public void testAddIndex() {
        List l = new NotNullElementsArrayList();
        l.add(0, new Object());
        assertEquals(1, l.size());
        assertNotNull(l.get(0));
        try {
            l.add(0, null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    public void testSet() {
        List l = new NotNullElementsArrayList();
        l.add(new Object());
        l.set(0, new Object());
        assertEquals(1, l.size());
        assertNotNull(l.get(0));
        try {
            l.set(0, null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

}
