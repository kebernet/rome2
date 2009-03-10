package net.java.rome2.impl.atom.io;

import org.jdom.input.SAXBuilder;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;

public class JDomUtils {

    private static class EmptyEntityResolver implements EntityResolver {
        public InputSource resolveEntity(String publicId, String systemId) {
            if (systemId != null && systemId.endsWith(".dtd")) {
                return EMPTY_INPUTSOURCE;
            }
            return null;
        }
    }

    private static final InputSource EMPTY_INPUTSOURCE = new InputSource(new ByteArrayInputStream(new byte[0]));

    private static final EntityResolver RESOLVER = new EmptyEntityResolver();

    public static SAXBuilder createSAXBuilder() {
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setEntityResolver(RESOLVER);
        saxBuilder.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        saxBuilder.setExpandEntities(false);
        return saxBuilder;
    }

}
