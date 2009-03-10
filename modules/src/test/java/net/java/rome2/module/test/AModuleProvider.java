/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.module.test;

import net.java.rome2.modules.EntryModuleParser;
import net.java.rome2.modules.GeneratorContext;
import net.java.rome2.modules.ModuleFactory;
import net.java.rome2.modules.ModuleProvider;
import net.java.rome2.modules.ParserContext;
import org.jdom.Element;

/**
 *
 * @author kebernet
 */
public class AModuleProvider implements ModuleProvider, ModuleFactory<AModule>, EntryModuleParser<AModule> {

    public void init(ParserContext context) {
        context.setFactory(AModuleProvider.class, this);
        context.registerHandler(this, "http://www.w3.org/2005/Atom", "category");
        context.registerHandler(this, "urn:test:test", "test-content");
    }

    public AModule create() {
        return new AModule();
    }

    public Class<? extends ModuleProvider> getProviderClass() {
        return AModuleProvider.class;
    }

    public boolean parseModule(ParserContext context, AModule module, Element e) {
        if(e.getName().equals("category") ){
            if(e.getAttributeValue("scheme") != null && e.getAttributeValue("scheme").equals("urn:test:scheme") ){
                module.setCategoryValue( e.getAttributeValue("term"));
                return true;
            }
        } else if(e.getName().equals("test-content") ){
            module.setTestValue(e.getTextTrim());
            return true;
        }
        return false;
    }

    public void init(GeneratorContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
