package net.java.rome2.atom;

public class TestAtomGenerator extends BeanTestCase {

    public void testBean() {
        AtomGenerator generator = new AtomGenerator();
        assertEquals(null, generator.getText());
        assertEquals(null, generator.getUri());
        assertEquals(null, generator.getVersion());
        generator.setText("t");
        generator.setUri("u");
        generator.setVersion("v");
        assertEquals("t", generator.getText());
        assertEquals("u", generator.getUri());
        assertEquals("v", generator.getVersion());
    }

    public void testClone() throws Exception {
        AtomGenerator generator = new AtomGenerator();
        generator.setText("t");
        generator.setUri("u");
        generator.setVersion("v");
        assertEquals(generator, generator.clone());
    }

    public static void assertEquals(AtomGenerator generator1, AtomGenerator generator2) {
        if (notNull(generator1, generator2)) {
            TestAtomElement.assertEquals(generator1, generator2);
            assertEquals(generator1.getText(), generator2.getText());
            assertEquals(generator1.getUri(), generator2.getUri());
            assertEquals(generator1.getVersion(), generator2.getVersion());
        }
    }
}