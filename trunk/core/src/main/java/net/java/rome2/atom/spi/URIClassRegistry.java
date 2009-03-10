package net.java.rome2.atom.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class URIClassRegistry {
    private Class defaultClass;
    private Map<String, Class> exactMap = new HashMap<String, Class>();
    private List<PrefixClass> prefixList = new ArrayList<PrefixClass>();

    private class PrefixClass {
        private String prefix;
        private Class klass;

        public PrefixClass(String prefix, Class klass) {
            this.prefix = prefix;
            this.klass = klass;
        }

        public boolean equals(PrefixClass other) {
            return prefix.equals(other.prefix);
        }
    }

    URIClassRegistry(Class defaultClass) {
        this.defaultClass = defaultClass;
    }

    public void register(String uri, Class klass) {
        if (uri == null) {
            throw new IllegalArgumentException("URI cannot be NULL");
        }
        int wildcard = uri.indexOf("*");
        if (wildcard > -1 && wildcard < (uri.length() - 1)) {
            throw new IllegalArgumentException("URI can only have '*' wilcard at the end");
        }
        if (klass == null) {
            throw new IllegalArgumentException("klass cannot be NULL");
        }
        try {
            klass.getConstructor();
        }
        catch (NoSuchMethodException ex) {
            throw new IllegalArgumentException(ex);
        }
        if (wildcard == -1) {
            exactMap.put(uri, klass);
        }
        else {
            String prefix = uri.substring(0, uri.length() - 1);
            PrefixClass pc = new PrefixClass(prefix, klass);
            int pos = prefixList.indexOf(pc);
            if (pos > -1) {
                prefixList.set(pos, pc);
            }
            else {
                prefixList.add(pc);
                Collections.sort(prefixList, new Comparator<PrefixClass>() {
                    public int compare(PrefixClass obj1, PrefixClass obj2) {
                        return obj2.prefix.length() - obj1.prefix.length();
                    }

                });
            }
        }
    }

    public void unregister(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("URI cannot be NULL");
        }
        if (uri.equals("*")) {
            register("*", defaultClass);
        }
        else {
            int wildcard = uri.indexOf("*");
            if (wildcard > -1 && wildcard < (uri.length() - 1)) {
                throw new IllegalArgumentException("URI can only have '*' wilcard at the end");
            }
            if (wildcard == -1) {
                exactMap.remove(uri);
            }
            else {
                String prefix = uri.substring(0, uri.length() - 1);
                PrefixClass pc = new PrefixClass(prefix, null);
                prefixList.remove(pc);
            }
        }
    }

    public List<String> getRegisteredURIs() {
        List<String> list = new ArrayList<String>(exactMap.keySet());
        for (PrefixClass pc : prefixList) {
            list.add(pc.prefix + "*");
        }
        return Collections.unmodifiableList(list);
    }

    public Class getClass(String uri) {
        Class klass = exactMap.get(uri);
        if (klass == null) {
            for (PrefixClass pc : prefixList) {
                if (uri.startsWith(pc.prefix)) {
                    return pc.klass;
                }
            }
        }
        return klass;
    }

    public Object createInstance(String uri) {
        Class klass = getClass(uri);
        if (klass == null) {
            throw new IllegalArgumentException("No registered class for '" + uri + "'");
        }
        else {
            try {
                return klass.newInstance();
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
