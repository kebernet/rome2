package net.java.rome2.atom.extension.bag;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BagDefinitions {
    private static final BagDefinitions BAG_DEFINITIONS = new BagDefinitions();

    public static BagDefinitions getDefinitions() {
        return BAG_DEFINITIONS;
    }

    public static class Element {
        private String name;
        private String xmlName;
        private Class type;
        private String[] attributes;
        private int minOccurs;
        private int maxOccurs;

        public Element(String name, String xmlName, Class type, String[] attributes, int minOccurs, int maxOccurs) {
            this.name = name;
            this.xmlName = xmlName;
            this.type = type;
            this.attributes = attributes.clone();
            this.minOccurs = minOccurs;
            this.maxOccurs = maxOccurs;
        }

        public String getName() {
            return name;
        }

        public String getXmlName() {
            return xmlName;
        }

        public Class getType() {
            return type;
        }

        public String[] getAttributes() {
            return attributes;
        }

        public int getMinOccurs() {
            return minOccurs;
        }

        public int getMaxOccurs() {
            return maxOccurs;
        }
    }

    private Map<String, Map<String, Element>> definitions = new HashMap<String, Map<String, Element>>();

    private BagDefinitions() {
    }

    public void addDefinition(String uri, Map<String, Element> definition) {
        definitions.put(uri, Collections.unmodifiableMap(new HashMap<String, Element>(definition)));
    }

    public Map<String, Element> get(String uri) {
        return definitions.get(uri);
    }

}
