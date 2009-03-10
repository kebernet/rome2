/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.java.rome2.modules.impl;

import net.java.rome2.module.test.AModule;
import net.java.rome2.module.test.AModuleProvider;
import net.java.rome2.modules.ModuleProvider;

import org.jdom.Element;
import org.jdom.Namespace;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author kebernet
 */
public class DefaultParserContextTest {
    public DefaultParserContextTest() {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void initializationTest() throws Exception {
        DefaultParserContext context = new DefaultParserContext();
        boolean found = false;

        for (ModuleProvider provider : context.getModuleProviders()) {
            if (provider.getClass() == AModuleProvider.class) {
                found = true;
            }
        }
        assert (found);
    }

    @Test
    public void testCategoryAndValueParse() throws Exception {
        DefaultParserContext context = new DefaultParserContext();
        Element e = new Element("category",
                Namespace.getNamespace("http://www.w3.org/2005/Atom"));
        e.setAttribute("scheme", "urn:test:scheme");
        e.setAttribute("term", "test term");
        context.beginParseContext();

        AModule module = (AModule) context.handleEntryElement(e);
        assert (module.getCategoryValue().equals("test term"));
        e = new Element("test-content", Namespace.getNamespace("urn:test:test"));
        e.setText("test content test");
        module = (AModule) context.handleEntryElement(e);
        assert (module.getTestValue().equals("test content test"));
        assert (module.getCategoryValue().equals("test term"));
    }

    @Test
    public void testCategoryParse() throws Exception {
        DefaultParserContext context = new DefaultParserContext();
        Element e = new Element("category",
                Namespace.getNamespace("http://www.w3.org/2005/Atom"));
        e.setAttribute("scheme", "urn:test:scheme");
        e.setAttribute("term", "test term");
        context.beginParseContext();

        AModule module = (AModule) context.handleEntryElement(e);
        assert (module.getCategoryValue().equals("test term"));
    }
}
