package net.java.rome2.atom;

public class AtomExtAttr {
    private String prefix;
    private String namespace;
    private String name;
    private String value;

    static void check(String prefix, String namespace, String name, String value) {
        if (prefix != null && (prefix.equals("") || prefix.equals(AtomConstants.XML_NS_PREFIX))) {
            throw new IllegalArgumentException("Prefix cannot be emtpy or 'xml'");
        }
        if (namespace == null || namespace.equals("") || namespace.equals(AtomConstants.ATOM_NS_URI) ||
            namespace.equals(AtomConstants.XML_NS_URI)) {
            throw new IllegalArgumentException("Namespace cannot be NULL, emtpy, 'atom' or 'xml'");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be NULL");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be NULL");
        }
    }

    AtomExtAttr(String prefix, String namespace, String name, String value) {
        check(prefix, namespace, name, value);
        this.prefix = prefix;
        this.namespace = namespace;
        this.name = name;
        setValue(value);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be NULL");
        }
        this.value = value;
    }

}
