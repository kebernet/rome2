package net.java.rome2.atom;

import net.java.rome2.utils.NotNullElementsArrayList;
import org.jdom.Element;

import java.util.List;

public class UnknownAtomExtension implements AtomExtension, Cloneable {
    private String uri;
    private String prefix;
    private List<Element> elements;

    public UnknownAtomExtension(String prefix, String uri) {
        if (uri == null || uri.length() == 0) {
            throw new IllegalArgumentException("URI cannot be empty or NULL");
        }
        this.uri = uri;
        this.prefix = prefix;
    }

    public String getUri() {
        return uri;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<Element> getElements() {
        return (elements != null) ? elements : (elements = new JDomElementArrayList());
    }

    @SuppressWarnings({"CloneDoesntCallSuperClone"})
    public AtomExtension clone() throws CloneNotSupportedException {
        UnknownAtomExtension uExt = new UnknownAtomExtension(prefix, uri);
        for (Element e : getElements()) {
            uExt.getElements().add((Element) e.clone());
        }
        return uExt;
    }

    private class JDomElementArrayList extends NotNullElementsArrayList<Element> {
        private boolean initialized;

        public void add(int index, Element element) {
            enforceUniqueNamespace(element);
            super.add(index, element);
        }


        public Element set(int index, Element element) {
            enforceUniqueNamespace(element);
            return super.set(index, element);
        }

        private void enforceUniqueNamespace(Element element) {
            if (!initialized) {
                initialized = true;
                uri = element.getNamespaceURI();
            }

            if (uri == null && element.getNamespaceURI() != null) {
                throw new IllegalArgumentException(
                        "Element URI '" + element.getNamespaceURI() + "' does not match Extension URI '" + uri + "'");
            }
            else if (!uri.equals(element.getNamespaceURI())) {
                throw new IllegalArgumentException(
                        "Element URI '" + element.getNamespaceURI() + "' does not match Extension URI '" + uri + "'");
            }
        }

    }
}
