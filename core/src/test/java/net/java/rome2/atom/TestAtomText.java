package net.java.rome2.atom;

import org.jdom.Element;

public class TestAtomText extends BeanTestCase {

    public void testBean() {
        AtomText atomText = new AtomText();
        assertEquals(null, atomText.getText());
        assertEquals(null, atomText.getHtml());
        assertEquals(null, atomText.getXhtml());
        assertEquals(AtomText.TEXT, atomText.getType());

        atomText.setText("i");
        assertEquals("i", atomText.getText());
        assertEquals(null, atomText.getHtml());
        assertEquals(null, atomText.getXhtml());
        assertEquals(AtomText.TEXT, atomText.getType());

        atomText.setHtml("h");
        assertEquals("h", atomText.getHtml());
        assertEquals(null, atomText.getText());
        assertEquals(null, atomText.getXhtml());
        assertEquals(AtomText.HTML, atomText.getType());

        atomText.setXhtml(new Element("div", AtomConstants.XHTML_NS_URI));
        assertEquals(null, atomText.getHtml());
        assertEquals(null, atomText.getText());
        assertNotNull(atomText.getXhtml());
        assertEquals("div", atomText.getXhtml().getName());
        assertEquals(AtomText.XHTML, atomText.getType());
    }

    public static void assertEquals(AtomText text1, AtomText text2) {
        if (notNull(text1, text2)) {
            assertEquals(text1.getType(), text2.getType());
            assertEquals(text1.getText(), text2.getText());
            assertEquals(text1.getHtml(), text2.getHtml());
            if (AtomText.XHTML.equals(text1.getType())) {
                assertNotNull(text1.getXhtml());
                assertNotNull(text2.getXhtml());
                assertFalse(text1.getXhtml() == text2.getXhtml());
                assertEquals(text1.getXhtml().getName(), text2.getXhtml().getName());
            }
        }
    }

    public void testClone() throws Exception {
        AtomText atomText = new AtomText();
        atomText.setText("t");
        assertEquals(atomText, atomText.clone());

        atomText.setHtml("h");
        assertEquals(atomText, atomText.clone());

        atomText.setXhtml(new Element("div", AtomConstants.XHTML_NS_URI));
        assertEquals(atomText, atomText.clone());
    }

}
