package net.java.rome2.atom;

import net.java.rome2.utils.NotNullElementsArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ExtensibleImpl implements Extensible {
    List<AtomExtension> extensions;

    public AtomExtension getExtension(String uri) {
        if (extensions != null) {
            for (AtomExtension ext : extensions) {
                if (ext.getUri().equals(uri)) {
                    return ext;
                }
            }
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public List<String> getExtensionURIs() {
        if (extensions == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> l = new ArrayList<String>(extensions.size());
        for (AtomExtension ext : extensions) {
            l.add(ext.getUri());
        }
        return Collections.unmodifiableList(l);
    }

    public List<AtomExtension> getExtensions() {
        return (extensions == null) ? (extensions = new NotNullElementsArrayList<AtomExtension>()) : extensions;
    }

    protected List<AtomExtension> cloneExtensions() throws CloneNotSupportedException {
        List<AtomExtension> l = new NotNullElementsArrayList<AtomExtension>();
        for (AtomExtension ext : getExtensions()) {
            l.add(ext.clone());
        }
        return l;
    }

}
