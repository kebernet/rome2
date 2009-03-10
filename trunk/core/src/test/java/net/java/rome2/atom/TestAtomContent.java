package net.java.rome2.atom;

import org.jdom.Element;

public class TestAtomContent extends BeanTestCase {

    public void testBean() {
        AtomContent atomContent = new AtomContent();
        assertEquals(null, atomContent.getType());
        assertEquals(null, atomContent.getText());
        assertEquals(null, atomContent.getHtml());
        assertEquals(null, atomContent.getXhtml());
        assertEquals(null, atomContent.getXml());
        assertEquals(null, atomContent.getBinary());

        atomContent.setType("a");
        atomContent.setText("t");
        assertEquals(AtomContent.TEXT, atomContent.getType());
        assertEquals("t", atomContent.getText());
        assertEquals(null, atomContent.getHtml());
        assertEquals(null, atomContent.getXhtml());
        assertEquals(null, atomContent.getXml());
        assertEquals(null, atomContent.getBinary());

        atomContent.setType("a");
        atomContent.setHtml("h");
        assertEquals(AtomContent.HTML, atomContent.getType());
        assertEquals(null, atomContent.getText());
        assertEquals("h", atomContent.getHtml());
        assertEquals(null, atomContent.getXhtml());
        assertEquals(null, atomContent.getXml());
        assertEquals(null, atomContent.getBinary());

        atomContent.setType("a");
        atomContent.setXhtml(new Element("div", AtomConstants.XHTML_NS_URI));
        assertEquals(AtomContent.XHTML, atomContent.getType());
        assertEquals(null, atomContent.getText());
        assertEquals(null, atomContent.getHtml());
        assertNotNull(atomContent.getXhtml());
        assertEquals(null, atomContent.getXml());
        assertEquals(null, atomContent.getBinary());

        atomContent.setType("application/xml");
        atomContent.setXml(new Element("div", AtomConstants.XHTML_NS_URI));
        assertEquals("application/xml", atomContent.getType());
        assertEquals(null, atomContent.getText());
        assertEquals(null, atomContent.getHtml());
        assertEquals(null, atomContent.getXhtml());
        assertNotNull(atomContent.getXml());
        assertEquals(null, atomContent.getBinary());

        atomContent.setType("application/jpeg");
        atomContent.setBinary(new byte[0]);
        assertEquals("application/jpeg", atomContent.getType());
        assertEquals(null, atomContent.getText());
        assertEquals(null, atomContent.getHtml());
        assertEquals(null, atomContent.getXhtml());
        assertEquals(null, atomContent.getXml());
        assertNotNull(atomContent.getBinary());
    }

    public static void assertEquals(AtomContent content1, AtomContent content2) {
        if (notNull(content1, content2)) {
            assertEquals(content1.getType(), content2.getType());
            assertEquals(content1.getText(), content2.getText());
            assertEquals(content1.getHtml(), content2.getHtml());
            if (AtomContent.XHTML.equals(content1.getType())) {
                assertNotNull(content1.getXhtml());
                assertNotNull(content2.getXhtml());
                assertFalse(content1.getXhtml() == content2.getXhtml());
                assertEquals(content1.getXhtml().getName(), content2.getXhtml().getName());
            }
            else if (content1.getXml() != null) {
                assertNotNull(content1.getXml());
                assertNotNull(content2.getXml());
                assertFalse(content1.getXml() == content2.getXml());
                assertEquals(content1.getXml().getName(), content2.getXml().getName());
            }
            else if (content1.getBinary() != null) {
                assertNotNull(content1.getBinary());
                assertNotNull(content2.getBinary());
                assertFalse(content1.getBinary() == content2.getBinary());
                assertEquals(content1.getBinary().length, content2.getBinary().length);
                for (int i = 0; i < content1.getBinary().length; i++) {
                    assertEquals(content1.getBinary()[i], content2.getBinary()[i]);
                }
            }
        }
    }

    public void testClone() throws Exception {
        AtomContent atomContent = new AtomContent();
        assertEquals(atomContent, atomContent.clone());

        atomContent = new AtomContent();
        atomContent.setText("t");
        assertEquals(atomContent, atomContent.clone());

        atomContent = new AtomContent();
        atomContent.setHtml("h");
        assertEquals(atomContent, atomContent.clone());

        atomContent = new AtomContent();
        atomContent.setXhtml(new Element("div", AtomConstants.XHTML_NS_URI));
        assertEquals(atomContent, atomContent.clone());

        atomContent = new AtomContent();
        atomContent.setType("application/xml");
        atomContent.setXml(new Element("div", AtomConstants.XHTML_NS_URI));
        assertEquals(atomContent, atomContent.clone());

        atomContent = new AtomContent();
        atomContent.setType("application/jpeg");
        atomContent.setBinary(new byte[0]);
        assertEquals(atomContent, atomContent.clone());
    }

}