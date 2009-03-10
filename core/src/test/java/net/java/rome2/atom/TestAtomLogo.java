package net.java.rome2.atom;

public class TestAtomLogo extends BeanTestCase {

    public void testBean() {
        AtomLogo atomLogo = new AtomLogo();
        assertEquals(null, atomLogo.getLogo());
        atomLogo.setLogo("i");
        assertEquals("i", atomLogo.getLogo());
    }

    public void testClone() throws Exception {
        AtomLogo Logo = new AtomLogo();
        Logo.setLogo("i");
        AtomLogo clone = Logo.clone();
        assertEquals(Logo, clone);
    }

    public static void assertEquals(AtomLogo logo1, AtomLogo logo2) {
        if (notNull(logo1, logo2)) {
            TestAtomElement.assertEquals(logo1, logo2);
            assertEquals(logo1.getLogo(), logo2.getLogo());

        }
    }
}