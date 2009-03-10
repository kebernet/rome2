package net.java.rome2.atom;

import java.util.HashSet;
import java.util.Set;

public class TestAtomElement extends BeanTestCase {

    public static class MyAtomElement extends AtomElement<MyAtomElement> {
    }

    public void testBean() {
        AtomElement atomElement = new MyAtomElement();
        assertEquals(null, atomElement.getXmlBase());
        assertEquals(null, atomElement.getXmlLang());
        assertEquals(new HashSet(), atomElement.getExtensionAttributes());
    }

    public static void assertEquals(AtomElement atomElement1, AtomElement atomElement2) {
        if (notNull(atomElement1, atomElement2)) {
            assertEquals(atomElement1.getXmlBase(), atomElement2.getXmlBase());
            assertEquals(atomElement1.getXmlLang(), atomElement2.getXmlLang());

            assertEquals(atomElement1.getExtensionAttributes().size(), atomElement2.getExtensionAttributes().size());
            for (AtomExtAttr attr1 : (Set<AtomExtAttr>) atomElement1.getExtensionAttributes()) {
                AtomExtAttr attr2 = atomElement2.getExtensionAttribute(attr1.getNamespace(), attr1.getName());
                assertEquals(attr1.getPrefix(), attr2.getPrefix());
                assertEquals(attr1.getValue(), attr2.getValue());
            }
        }
    }

    public void testClone() throws Exception {
        AtomElement atomElement = new MyAtomElement();
        atomElement.setXmlBase("a");
        atomElement.setXmlLang("b");
        atomElement.setExtensionAttribute("a", "A", "b", "B");
        atomElement.setExtensionAttribute(null, "AA", "bb", "BB");

        assertEquals(atomElement, atomElement.clone());
    }

    public void testXmlAttributes() {
        AtomElement atomElement = new MyAtomElement();

        atomElement.setXmlBase("a");
        assertEquals("a", atomElement.getXmlBase());
        atomElement.setXmlBase(null);
        assertEquals(null, atomElement.getXmlBase());

        atomElement.setXmlLang("b");
        assertEquals("b", atomElement.getXmlLang());
        atomElement.setXmlLang(null);
        assertEquals(null, atomElement.getXmlLang());
    }

    public void testExtensionAttributes() {
        AtomElement atomElement = new MyAtomElement();

        atomElement.setExtensionAttribute("a", "A", "b", "BBB");

        assertEquals(1, atomElement.getExtensionAttributes().size());
        assertEquals(1, atomElement.getExtensionAttributes("A").size());

        atomElement.setExtensionAttribute("a", "A", "b", "B");
        assertEquals(1, atomElement.getExtensionAttributes().size());
        assertEquals(1, atomElement.getExtensionAttributes("A").size());

        AtomExtAttr attribute = atomElement.getExtensionAttribute("A", "b");
        assertEquals("a", attribute.getPrefix());
        assertEquals("A", attribute.getNamespace());
        assertEquals("b", attribute.getName());
        assertEquals("B", attribute.getValue());

        attribute.setValue("C");
        assertEquals("C", attribute.getValue());

        atomElement.setExtensionAttribute(null, "AA", "bb", "BB");

        assertEquals(2, atomElement.getExtensionAttributes().size());
        assertEquals(1, atomElement.getExtensionAttributes("A").size());
        assertEquals(1, atomElement.getExtensionAttributes("AA").size());

        attribute = atomElement.getExtensionAttribute("AA", "bb");
        assertEquals(null, attribute.getPrefix());
        assertEquals("AA", attribute.getNamespace());
        assertEquals("bb", attribute.getName());
        assertEquals("BB", attribute.getValue());

        atomElement.removeExtensionAttribute("A", "b");
        assertEquals(1, atomElement.getExtensionAttributes().size());
        assertEquals(0, atomElement.getExtensionAttributes("A").size());
        assertEquals(1, atomElement.getExtensionAttributes("AA").size());

        try {
            atomElement.getExtensionAttributes().remove(0);
            fail();
        }
        catch (UnsupportedOperationException ex) {
        }

        try {
            atomElement.getExtensionAttributes("AA").remove(0);
            fail();
        }
        catch (UnsupportedOperationException ex) {
        }
    }

}
