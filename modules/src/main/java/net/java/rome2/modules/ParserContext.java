/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.java.rome2.modules;

import org.jdom.Element;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The ParserContext is a relatively complicated class that is used for dealing with
 * ModuleProviders and building modules during the feed parse operation.
 *
 * The DefaultParserContext class provides a basic implementation
 * @see net.java.dev.rome2.modules.impl.DefaultParserContext
 * @author kebernet
 */
public class ParserContext {
    private static final Set<ModuleParser> EMPTY_PARSER_SET = Collections.unmodifiableSet(new HashSet<ModuleParser>());
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private Map<Class<?extends ModuleProvider>, Module> currentContextModules = null;
    private Map<String, Map<String, Set<ModuleParser>>> entry = null;
    private Map<Class<?extends ModuleProvider>, ModuleFactory> factories = new HashMap<Class<?extends ModuleProvider>, ModuleFactory>();
    private Map<String, Map<String, Set<ModuleParser>>> feed = null;
    private Map<String, Map<String, Set<ModuleParser>>> globals = null;
    private Set<ModuleProvider> moduleParserProviders;

    /**
     * Sets an attribute on the ParserContext
     * @param key
     * @param value
     */
    public void setAttribute(String key, Object value) {
        assert key != null : "Key cannot be null";
        assert value != null : "Value cannot be null";
        this.attributes.put(key, value);
    }

    /**
     * Get an attibute from the parser context
     * @param key The string key
     * @return the Object value
     */
    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    /**
     * Sets the factories for a ModuleProvider
     * @param factories The factories mapped to ModuleProvider classes.
     */
    public void setFactories(
        Map<Class<?extends ModuleProvider>, ModuleFactory> factories) {
        this.factories = factories;
    }

    /**
     * @return the factories mapped to ModuleProvider classes.
     */
    public Map<Class<?extends ModuleProvider>, ModuleFactory> getFactories() {
        return factories;
    }

    /**
     * Sets a factory class for a particular ModuleProvider class instance
     *
     * @param providerClass Provider class to register
     * @param factory factory to register.
     */
    public void setFactory(Class<?extends ModuleProvider> providerClass,
        ModuleFactory factory) {
        this.factories.put(providerClass, factory);
    }

    public ModuleFactory getModuleFactory(Class<?extends ModuleProvider> clazz) {
        return this.factories.get(clazz);
    }

    /**
     * Set the value of moduleParserProviders.
     *
     * These are the ModuleProvider implementations that will be used by this
     * parser. As this property is set, each of the providers will be initialized
     * to this context.
     *
     * @param newModuleParserProviderrs new value of ModuleParserProviders
     */
    public void setModuleProviders(
        Set<ModuleProvider> newModuleProviderrs) {
        assert newModuleProviderrs != null : "Providers set cannot be null";
        this.moduleParserProviders = newModuleProviderrs;
        this.initializeProviders();
    }

    /**
     * Get the value of moduleParserProviders
     *
     * @return the value of ModuleParserProviders
     */
    public Set<ModuleProvider> getModuleProviders() {
        return this.moduleParserProviders;
    }

    /**
     * Intializes the cached module instances for a new context (feed, or each entry);
     */
    public void beginParseContext() {
        this.currentContextModules = new HashMap<Class<?extends ModuleProvider>, Module>();
    }

    /**
     * Parses an element within an entry
     * @param e Element to parse
     * @return a Module, or Null if it is unhandled. Modules can iteratively clobber.
     */
    public Module handleEntryElement(Element e) {
        assert e != null : "Null element to handle";
        return this.handleElement(this.entry, e);
    }

    /**
     * Parses a feed element and returns a module refernece or null
     * @param e Element to parse
     * @return Module or null.
     */
    public Module handleFeedElement(Element e) {
        assert e != null : "Null element to handle";
        return this.handleElement(this.feed, e);
    }

    /**
     * Registers a new handler for a node
     * @param parser The ModuleParser to use
     * @param namespace the Namespace URI
     * @param nodeName the NodeName or null for the whole namespace.
     */
    public void registerHandler(ModuleParser parser, String namespace,
        String nodeName) {
        assert parser != null : "You must provide a parser";
        assert namespace != null : "You must provide a namespace";
        nodeName = (nodeName == null) ? "*" : nodeName;

        if (parser instanceof EntryModuleParser) {
            this.registerModuleParser(this.entry, parser, namespace, nodeName);
        } else if (parser instanceof FeedModuleParser) {
            this.registerModuleParser(this.feed, parser, namespace, nodeName);
        } else {
            this.registerModuleParser(this.globals, parser, namespace, nodeName);
        }
    }

    /**
     * This method initializes the maps for the tag parsers and calls init() on
     * each of the module providers.
     */
    protected void initializeProviders() {
        this.feed = new HashMap<String, Map<String, Set<ModuleParser>>>();
        this.entry = new HashMap<String, Map<String, Set<ModuleParser>>>();
        this.globals = new HashMap<String, Map<String, Set<ModuleParser>>>();

        for (ModuleProvider provider : this.getModuleProviders()) {
            provider.init(this);
        }
    }

    private Set<ModuleParser> getModuleParsers(
        Map<String, Map<String, Set<ModuleParser>>> contextMap,
        String namespace, String nodeName) {
        Map<String, Set<ModuleParser>> nodes = contextMap.get(namespace);

        if (nodes == null) {
            return ParserContext.EMPTY_PARSER_SET;
        }

        return nodes.get(nodeName);
    }

    private Module handlSingleNode(
        Map<String, Map<String, Set<ModuleParser>>> baseContextMap,
        String nameClobber, Element e) {
        for (ModuleParser p : this.getModuleParsers(baseContextMap,
                e.getNamespaceURI(), nameClobber)) {
            Module result = result = currentContextModules.get(p.getProviderClass());

            if (result == null) {
                result = this.factories.get(p.getProviderClass()).create();
                this.currentContextModules.put(p.getProviderClass(), result);
            }

            boolean handled = p.parseModule(this, result, e);

            if (handled) {
                return result;
            }
        }

        return null;
    }

    /**
     * Handles a module inside a single context map
     * @param baseContextMap context map to search
     * @param e element to parse
     * @return Module or Null.
     */
    private Module handleElement(
        Map<String, Map<String, Set<ModuleParser>>> baseContextMap, Element e) {
        Module result = this.handleSingleMap(this.entry, e);

        if (result == null) {
            result = this.handleSingleMap(this.globals, e);
        }

        return result;
    }

    /**
     * Searches a single map of module parser for nodeName or wildcard
     * @param baseContextMap map to search
     * @param e Element to parse
     * @return Module or Null.
     */
    private Module handleSingleMap(
        Map<String, Map<String, Set<ModuleParser>>> baseContextMap, Element e) {
        Module result = this.handlSingleNode(baseContextMap, e.getName(), e);

        if (result != null) {
            return result;
        } else {
            result = this.handlSingleNode(baseContextMap, "*", e);
        }

        return result;
    }

    /**
     * Registers a module parser for a tag to a particular map.
     * @param contextMap the map to use (feed, entry, global)
     * @param parser the module parser to register
     * @param namespace namespace for the registry
     * @param nodeName nodeName to register
     */
    private void registerModuleParser(
        Map<String, Map<String, Set<ModuleParser>>> contextMap,
        ModuleParser parser, String namespace, String nodeName) {
        Map<String, Set<ModuleParser>> elements = contextMap.get(namespace);

        if (elements == null) {
            elements = new HashMap<String, Set<ModuleParser>>();
            contextMap.put(namespace, elements);
        }

        Set<ModuleParser> node = elements.get(nodeName);

        if (node == null) {
            node = new HashSet<ModuleParser>();
            elements.put(nodeName, node);
        }

        node.add(parser);
    }
}
