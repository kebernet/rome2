/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.modules;

import org.jdom.Element;

/**
 * ModuleParser is an interface that parses data from an element into a module 
 * implementation.
 * 
 * ModuleParser direct implementations will be registered globally -- that is
 * at both the Feed and Entry levels
 * @see EntryModuleParser
 * @see FeedModuleParser
 * @author kebernet
 */
public interface ModuleParser<T extends Extension> {

    Class<? extends ModuleProvider> getProviderClass();

    /** 
     * @return Whether the element was handled by the module parser or not
     */
     boolean parseModule(ParserContext context, T module, Element e);
}
