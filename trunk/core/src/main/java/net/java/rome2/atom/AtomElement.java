package net.java.rome2.atom;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class AtomElement<B extends AtomElement> implements Cloneable {
    private String xmlLang;
    private String xmlBase;
    private Map<String, AtomExtAttr> extensionAttributes;

    public String getXmlLang() {
        return xmlLang;
    }

    public void setXmlLang(String xmlLang) {
        this.xmlLang = xmlLang;
    }

    public String getXmlBase() {
        return xmlBase;
    }

    public void setXmlBase(String xmlBase) {
        this.xmlBase = xmlBase;
    }

    @SuppressWarnings({"CloneDoesntCallSuperClone", "unchecked"})
    public B clone() throws CloneNotSupportedException {
        try {
            AtomElement bean = this.getClass().newInstance();
            bean.setXmlLang(xmlLang);
            bean.setXmlBase(xmlBase);
            for (AtomExtAttr attr : getExtensionAttributes()) {
                bean.setExtensionAttribute(attr.getPrefix(), attr.getNamespace(), attr.getName(), attr.getValue());
            }
            return (B) bean;
        }
        catch (IllegalAccessException ex) {
            throw new CloneNotSupportedException(ex.getMessage());
        }
        catch (InstantiationException ex) {
            throw new CloneNotSupportedException(ex.getMessage());
        }
    }

    public Set<AtomExtAttr> getExtensionAttributes() {
        if (extensionAttributes == null) {
            extensionAttributes = new LinkedHashMap<String, AtomExtAttr>();
        }
        return Collections.unmodifiableSet(new LinkedHashSet<AtomExtAttr>(extensionAttributes.values()));
    }

    @SuppressWarnings("unchecked")
    public Set<AtomExtAttr> getExtensionAttributes(String namespace) {
        AtomExtAttr.check("dummy", namespace, "dummy", "dummy");
        Set<AtomExtAttr> set = null;
        if (extensionAttributes != null) {
            for (AtomExtAttr attr : extensionAttributes.values()) {
                if (attr.getNamespace().equals(namespace)) {
                    if (set == null) {
                        set = new LinkedHashSet<AtomExtAttr>();
                    }
                    set.add(attr);
                }
            }
        }
        return (set != null) ? Collections.unmodifiableSet(set) : Collections.EMPTY_SET;
    }

    public AtomExtAttr getExtensionAttribute(String namespace, String name) {
        AtomExtAttr.check("dummy", namespace, name, "dummy");
        return extensionAttributes.get(namespace + ":" + name);
    }

    public void setExtensionAttribute(String prefix, String namespace, String name, String value) {
        AtomExtAttr attr = new AtomExtAttr(prefix, namespace, name, value);
        if (extensionAttributes == null) {
            extensionAttributes = new LinkedHashMap<String, AtomExtAttr>();
        }
        extensionAttributes.put(namespace + ":" + name, attr);
    }

    public void removeExtensionAttribute(String namespace, String name) {
        AtomExtAttr.check("dummy", namespace, name, "dummy");
        if (extensionAttributes != null) {
            extensionAttributes.remove(namespace + ":" + name);
        }
    }

}
