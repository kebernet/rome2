package net.java.rome2.atom;

import java.util.List;

public class TestExtensibleImpl extends BeanTestCase {

    public void testBean() {
        Extensible ext = new ExtensibleImpl();
        assertEquals(0, ext.getExtensions().size());
        assertEquals(0, ext.getExtensionURIs().size());
        ext.getExtensions().add(new UnknownAtomExtension("p", "u"));
        assertEquals(1, ext.getExtensions().size());
        assertEquals(1, ext.getExtensionURIs().size());
        ext.getExtensions().add(new UnknownAtomExtension("pp", "uu"));
        assertEquals(2, ext.getExtensions().size());
        assertEquals(2, ext.getExtensionURIs().size());
        assertNotNull(ext.getExtension("u"));
        assertNotNull("p", ext.getExtension("u").getPrefix());
        assertNotNull(ext.getExtension("uu"));
        assertNotNull("pp", ext.getExtension("uu").getPrefix());
    }

    public static void assertEquals(Extensible extensible1, Extensible extensible2) {
        if (notNull(extensible1, extensible2)) {
            assertEquals(extensible1.getExtensions().size(), extensible2.getExtensions().size());
            List<String> extensible2URIs = extensible2.getExtensionURIs();
            for (String uri : extensible1.getExtensionURIs()) {
                assertTrue(extensible2URIs.contains(uri));
            }
        }
    }
}
