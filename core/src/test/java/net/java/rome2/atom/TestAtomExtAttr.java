package net.java.rome2.atom;

import junit.framework.TestCase;

public class TestAtomExtAttr extends TestCase {


    public void testExtensionAttributesProperties() {
        AtomExtAttr attr = new AtomExtAttr("a", "A", "b", "B");
        assertEquals("a", attr.getPrefix());
        assertEquals("A", attr.getNamespace());
        assertEquals("b", attr.getName());
        assertEquals("B", attr.getValue());

        attr = new AtomExtAttr(null, "A", "b", "B");
        assertEquals(null, attr.getPrefix());
    }

    public void testIllegalArguments() {
        try {
            AtomExtAttr attr = new AtomExtAttr("a", "A", "b", "B");
            attr.setValue(null);
            fail();
        }
        catch (IllegalArgumentException ex) {
        }
        try {
            new AtomExtAttr("", "A", "b", "B");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }

        try {
            new AtomExtAttr("a", AtomConstants.ATOM_NS_URI, "b", "B");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }

        try {
            new AtomExtAttr("a", "", "b", "B");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }

        try {
            new AtomExtAttr("a", AtomConstants.XML_NS_URI, "b", "B");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }

        try {
            new AtomExtAttr("xml", "A", "b", "B");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }
        try {
            new AtomExtAttr("a", null, "b", "B");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }
        try {
            new AtomExtAttr("a", "A", null, "B");
            fail();
        }
        catch (IllegalArgumentException ex) {
        }
        try {
            new AtomExtAttr("a", "A", "b", null);
            fail();
        }
        catch (IllegalArgumentException ex) {
        }
    }
}