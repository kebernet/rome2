package net.java.rome2.atom.extension.bag;

import net.java.rome2.atom.AtomExtension;
import net.java.rome2.utils.CloneUtils;
import net.java.rome2.utils.NotNullElementsArrayList;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BagAtomExtension implements AtomExtension {
    private String uri;
    private String prefix;
    private Map<String, List> bag = new LinkedHashMap<String, List>();
    private Map<String, BagDefinitions.Element> definition;

    public BagAtomExtension(String uri, String prefix) {
        this.uri = uri;
        this.prefix = prefix;
        definition = BagDefinitions.getDefinitions().get(uri);
    }

    @SuppressWarnings({"CloneDoesntCallSuperClone", "unchecked"})
    public AtomExtension clone() throws CloneNotSupportedException {
        BagAtomExtension extension = new BagAtomExtension(getUri(), getPrefix());
        for (String name : getNames()) {
            for (Object element : getList(name, false)) {
                List list = extension.getList(name, true);
                list.add(CloneUtils.clone(element));
            }
        }
        return extension;
    }

    public String getUri() {
        return uri;
    }

    public String getPrefix() {
        return prefix;
    }

    private List getList(String name, boolean create) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be NULL");
        }
        checkName(name);
        List list = bag.get(name);
        if (list == null && create) {
            list = new NotNullElementsArrayList();
            bag.put(name, list);
        }
        return list;
    }

    @SuppressWarnings({"unchecked"})
    public void set(String name, Object value) {
        checkValue(name, value);
        List list = getList(name, true);
        list.clear();
        list.add(value);
    }

    @SuppressWarnings({"unchecked"})
    public void add(String name, Object value) {
        checkValue(name, value);
        List list = getList(name, true);
        list.add(value);
    }

    public Set<String> getNames() {
        return Collections.unmodifiableSet(bag.keySet());
    }

    public Object getValue(String name) {
        List list = getList(name, true);
        return (list.size() == 0) ? null : list.get(0);
    }

    @SuppressWarnings({"unchecked"})
    public List getValues(String name) {
        return getList(name, false);
    }

    public void clearElements(String name) {
        List list = getList(name, false);
        if (list != null) {
            list.clear();
        }
    }

    protected void checkName(String name) throws IllegalArgumentException {
        if (definition != null && !definition.containsKey(name)) {
            throw new IllegalArgumentException("Undefined element: " + name);
        }
    }

    protected void checkValue(String name, Object value) throws IllegalArgumentException {
        checkName(name);
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be NULL");
        }
        if (definition != null) {
            BagDefinitions.Element elementDef = definition.get(name);
            if (elementDef.getType() != value.getClass()) {
                throw new IllegalArgumentException("Element '" + name + "' wrong value type '" + value.getClass() +
                                                   "', it should be '" + elementDef.getType() + "'");
            }
        }
    }

}
